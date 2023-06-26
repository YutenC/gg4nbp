package gg.nbp.web.SecondHand.buy.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistPictureDao;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;

@Service
public class SecondHandBuyServiceImpl implements SecondHandBuyService {

	@Autowired
	private SecondHandBuylistDao dao;
	@Autowired
	private SecondHandBuylistPictureDao daoPic;
	
	@Autowired
	private MemberDao daoMember;

	/* 交易控制 : 新增事件 */
	@Transactional
	@Override
	public BuyEvent submit(SecondhandBuylist sl, Integer id) {

		/* 將文字資料放入資料庫 */
		sl.setSuccessful(insert(sl,id));

		/* 將圖片資料放入資料庫 */
		try {

			for (SecondhandBuyPicture img : sl.getImage())
				insertimg((SecondhandBuyPicture) img, sl.getBuylistId());
			
		} catch (Exception e) {
			sl.setMessage("沒有圖片");
		}	
		return new BuyEvent(sl,daoMember);
	};

	/* 交易控制 : 刪除事件 */
	@Transactional
	@Override
	public boolean delete(Integer memberId, Integer eventId) throws SQLException {

		/* 找到要刪除的事件 */
		SecondhandBuylist sl = dao.selectById(eventId);

		/* 驗證發出刪除請求的人是否為事件的所有人，若請求人非所有人則丟出例外 */
		if (memberId != sl.getMemberId())
			throw new SQLException();

		/* 因為有外來鍵的限制，先刪除圖片 */
		List<SecondhandBuyPicture> imgList = selectimg(sl);
		for (SecondhandBuyPicture img : imgList)
			delImg(img);

		/* 再刪除事件 */
		return delbuyList(sl);

	}

	/* 搜尋全部 */
	@Override
	public List<BuyEvent> searchAll() throws SQLException {

		/* 建立回傳用的List */
		List<BuyEvent> listDTO = new ArrayList<>();

		/**************************************************************************************
		 * 因為 SecondhandBuylist 的 image 沒有被持久化(Transient)，所以圖片必須自己搜尋再注入 順便遍歷一下 dao
		 * 抓回來的結果，將其轉化為 DTO
		 **************************************************************************************/
		// 有辦法優化 ?
		for (SecondhandBuylist sl : dao.selectAll()) {
			sl.setImage(selectimg(sl));
			listDTO.add(new BuyEvent(sl,daoMember));
		}

		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new SQLException();

		/* 回傳 */
		return listDTO;
	}

	
	/* ID 搜尋 */
	@Override
	public List<BuyEvent> searchById(Integer id) {
		SecondhandBuylist sl = dao.selectById(id);
		sl.setImage(selectimg(sl));
		List<BuyEvent> list = new ArrayList<>();
		list.add(new BuyEvent(sl,daoMember));
		return list;
	}
	
	
	/* 名字搜尋 */
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
		for (SecondhandBuylist sl : dao.selectByName(name)) {
			sl.setImage(selectimg(sl));
			listDTO.add(new BuyEvent(sl,daoMember));
		}
		
		/* 如果抓到 0 筆資料，則拋出例外 */
		if (listDTO.size() == 0)
			throw new SQLException();
		
		/* 回傳 */
		return listDTO;
	}
	
	
	
	@Transactional
	@Override
	public boolean update(BuyEvent be) {
		dao.update(BuyEvent.toSecondhandBuylist(be, dao));
		return true;
	}
	
	
	public void insertimg(SecondhandBuyPicture img, Integer id) {
		daoPic.insert(new SecondhandBuyPicture(id, img.getImage()));
	}
	
	public boolean insert(SecondhandBuylist s , Integer id) {

		s.setMemberId(id);
		s.setPayState(0); // 預設為 0
		s.setApprovalState("0"); // 預設為 0
		dao.insert(s);
		return true;
	}

	public List<SecondhandBuyPicture> selectimg(SecondhandBuylist s) {
		return daoPic.selectBylistId(s.getBuylistId());
	}

	

	public boolean delImg(SecondhandBuyPicture s) {
		daoPic.deleteById(s.getImageId());
		return true;
	}

	public boolean delbuyList(SecondhandBuylist sl) {
		dao.deleteById(sl.getBuylistId());
		return true;
	}

	
	
	

}