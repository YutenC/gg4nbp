package gg.nbp.web.SecondHand.buy.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import gg.nbp.web.SecondHand.buy.util.Toolbox;
import redis.clients.jedis.Jedis;

@Service
public class SecondHandBuyServiceImpl implements SecondHandBuyService {

	@Autowired
	private SecondHandBuylistDao dao;
	@Autowired
	private MemberDao daoMember;
	@Autowired
	private Jedis jedis;
	@Autowired
	private NoticeService nsrv ;

	/* 交易控制 : 新增事件 */
	@Transactional
	@Override
	public BuyEvent submit(SecondhandBuylist sl, Integer id) throws SQLException {
		String key = "event" + sl.getBuylistId() ;
		
		jedis.select(9);
		String check = jedis.get(key) == null ? "-1" : jedis.get(key);
		switch (check) {
			case "5" -> throw new SQLException();
			case "1","2","3","4" -> jedis.incrBy(key, 1);
			default ->{
				jedis.set(key, "1");
				jedis.expire(key,(long) 30);
			}
		}
		
		
		/* 將資料放入資料庫 */
		sl.setSuccessful(insert(sl,id));

		
		return new BuyEvent(sl,daoMember);
	};

	/* 交易控制 : 刪除事件 */
	@Transactional
	@Override
	public boolean delete(Integer memberId, Integer eventId) throws SQLException {

		/* 找到要刪除的事件 */
		var sl = dao.selectById(eventId);
		
		/* 確認案件進度 : 已完成議價的案件不允許刪除 */
		if(BuyEvent.getProgress(sl) >= 3 && BuyEvent.getProgress(sl) < 100) 
			throw new SQLException();

		/* 驗證發出刪除請求的人是否為事件的所有人，若請求人非所有人則丟出例外 */
		if (!memberId.equals(sl.getMemberId()))
			throw new SQLException();

		/* 再刪除事件 */
		dao.deleteById(sl.getBuylistId());
		return true;

	}

	/* 搜尋全部 */
	@Override
	public List<BuyEvent> searchAll() throws SQLException {


		/**************************************************************************************
		 * 因為 SecondhandBuylist 的 image 沒有被持久化(Transient)，所以圖片必須自己搜尋再注入 順便遍歷一下 dao
		 * 抓回來的結果，將其轉化為 DTO
		 **************************************************************************************/
		// 有辦法優化 ?
		List<BuyEvent> listDTO = dao.selectAll().stream()
					   				.filter(p -> p.getPayState() != 2)  //篩選掉已經完成的案件
					   				.filter(p -> !(p.getApprovalState().equals("7") || p.getApprovalState().equals("3") || p.getApprovalState().equals("4")))  //篩選掉不成立的案件
					   				.map(sl -> new BuyEvent(sl,daoMember))
					   				.collect(Collectors.toList());

		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new NullPointerException();

		/* 回傳 */
		return listDTO;
	}

	/* 用會員搜尋全部 */
	@Override
	public List<BuyEvent> searchAll(Member member) throws SQLException {
		
		/**************************************************************************************
		 * 因為 SecondhandBuylist 的 image 沒有被持久化(Transient)，所以圖片必須自己搜尋再注入 順便遍歷一下 dao
		 * 抓回來的結果，將其轉化為 DTO
		 **************************************************************************************/
		// 有辦法優化 ?		
		List<BuyEvent> listDTO = dao.selectByMemberId(member.getMember_id())
									.stream()
									.filter(sl -> !sl.getApprovalState().equals("7"))
									.map(sl -> new BuyEvent(sl,daoMember))
									.collect(Collectors.toList());

		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new NullPointerException();

		/* 回傳 */
		return listDTO;
	}
	
	/* ID 搜尋 */
	@Override
	public List<BuyEvent> searchById(Integer id) {
		SecondhandBuylist sl = dao.selectById(id);
		List<BuyEvent> list = new ArrayList<>();
		list.add(new BuyEvent(sl,daoMember));
		return list;
	}
	
	/* 商品名字搜尋 */
	@Override
	public List<BuyEvent> searchByName(String name) throws SQLException {
		/* 建立回傳用的List */
		List<BuyEvent> listDTO = new ArrayList<>();
		
		
		
		/* 如果傳入空字串，則拋出例外 ( trim()已經在controller層做過了 )*/
		if(name.isEmpty())
			throw new SQLException();
		
		/**************************************************************************************
		 * 因為 SecondhandBuylist 的 image 沒有被持久化(Transient)，所以圖片必須自己搜尋再注入 順便遍歷一下 dao
		 * 抓回來的結果，將其轉化為 DTO
		 **************************************************************************************/
		dao.selectByName(name).stream()
							  .forEach(sl -> listDTO.add(new BuyEvent(sl,daoMember)));
		
		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new NullPointerException();
		
		/* 回傳 */
		return listDTO;
	}
	
	/* 商品名字搜尋(會員) */
	@Override
	public List<BuyEvent> searchByName(String name, Member member) throws SQLException {
		
		/* 建立回傳用的List */
		List<BuyEvent> listDTO = new ArrayList<>();
		
		/* 如果傳入空字串，就去搜尋全部*/
		if(name.isEmpty())
			return searchAll(member);
		
		/**************************************************************************************
		 * 因為 SecondhandBuylist 的 image 沒有被持久化(Transient)，所以圖片必須自己搜尋再注入 順便遍歷一下 dao
		 * 抓回來的結果，將其轉化為 DTO
		 **************************************************************************************/
		dao.selectByName4Member(name,member.getMember_id()).stream()
														   .filter(sl -> !sl.getApprovalState().equals("7"))
														   .forEach(sl -> listDTO.add(new BuyEvent(sl,daoMember)));
		
		
		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new NullPointerException();
		
		/* 回傳 */
		return listDTO;
	}
	
	/* 交易控制 : 修改資料 */
	@Transactional
	@Override
	public List<BuyEvent> update4Mana(BuyEvent be) {
		var sl =  BuyEvent.trans4Mana(be, dao);
		dao.update(sl);
		
		if(sl.getMessage() != null) 
			nsrv.addNotice(Toolbox.sendNotice(sl));
			
		return searchById(be.getEventId());
	}
	
	/* 交易控制 : 修改資料 */
	@Transactional
	@Override
	public List<BuyEvent> update4Mem(BuyEvent be, Member member) throws SQLException,NullPointerException{
		/* 如果訂單不屬於該會員則丟出例外 */
		if(!searchById(be.getEventId()).get(0).getMemberId().equals(member.getMember_id()))
			throw new SQLException();
		dao.update(BuyEvent.trans4Mem(be, dao));
		return searchById(be.getEventId());
	}
	
	
	
	@Transactional
	@Override
	public void clearEvent(){
		System.out.println("即將執行每日清掃工作 : 清除未完成二手收購申請");
		System.out.println("現在時間 : "+Toolbox.getNow());
		Calendar k = Calendar.getInstance();
		k.setTime(new Date());
		dao.selectAll().stream()
					   .filter(p -> p.getApprovalState().equals("7"))
					   .filter(p -> {
						   Calendar c = Calendar.getInstance();
						   c.setTime(p.getApplyTime());
						   return c.get(Calendar.DATE) != k.get(Calendar.DATE) ;
					   })
					   .forEach(el -> dao.deleteById(el.getBuylistId()));
	}
	
	
	
	
	public boolean insert(SecondhandBuylist s , Integer id) {

		s.setMemberId(id);
		s.setPayState(0); // 預設為 0
		s.setApprovalState("7"); // 預設為 7
		dao.insert(s);
		return true;
	}



	

	
	
	

}