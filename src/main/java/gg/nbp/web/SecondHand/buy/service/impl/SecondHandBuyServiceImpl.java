package gg.nbp.web.SecondHand.buy.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistPictureDao;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;


@Service
public class SecondHandBuyServiceImpl implements SecondHandBuyService{

	@Autowired
	private SecondHandBuylistDao dao;
	@Autowired
	private SecondHandBuylistPictureDao daoPic;
	
	
	
	
	
	/* 交易控制 : 全做 或 全不做 */
	@Transactional
	@Override
	public SecondhandBuylist submit(SecondhandBuylist sl) {
		
		/* 將文字資料放入資料庫 */
		sl.setSuccessful(insert(sl));
		
		/* 將圖片資料放入資料庫 */
		int buylistId = sl.getBuylistId();
		
		try {
			
			for (SecondhandBuyPicture img : sl.getImage()) 
				insertimg((SecondhandBuyPicture)img, buylistId);
		} catch (Exception e) {
			sl.setMessage("沒有圖片");
		}
		return sl;
	};
	
	
	/* 交易控制 : 全做 或 全不做 */
	@Transactional
	@Override
	public boolean delete(Integer memberId , Integer eventId) throws SQLException {
		
		/* 找到要刪除的事件 */
		SecondhandBuylist sl = selectOne(eventId);
		
		
		/* 驗證發出刪除請求的人是否為事件的所有人，若請求人非所有人則丟出例外 */
		if(memberId != sl.getMemberId()) 
			throw new SQLException();
		

		/* 因為有外來鍵的限制，先刪除圖片 */
		List<SecondhandBuyPicture> imgList = selectimg(sl);
		for (SecondhandBuyPicture img : imgList) 
			delImg(img);
		
		
		/* 再刪除事件 */
		return delbuyList(sl);
		
	}
	
	
	
	
	public boolean insert(SecondhandBuylist s) {
		
		s.setMemberId(1);			
		s.setPayState(0);			// 預設為 0
		s.setApprovalState("0");	// 預設為 0
		dao.insert(s);
		return true;
	}


	public boolean insertimg(SecondhandBuyPicture img ,Integer id) {
		String url = "C:\\Users\\Tibame_T14\\Desktop\\AppImage\\"+img.getImage();
		SecondhandBuyPicture pic = new SecondhandBuyPicture();
		pic.setBuylistId(id);
		pic.setImage(url);
		daoPic.insert(pic);
		return true;
	}
	
	
	



	@Override
	public List<SecondhandBuylist> selectAll() {
		return dao.selectAll();
	}



	@Override
	public List<SecondhandBuyPicture> selectimg(SecondhandBuylist s) {
		return daoPic.selectBylistId(s.getBuylistId());
	}

	@Override
	public SecondhandBuylist selectOne(Integer i) {
		return dao.selectById(i);
	}


	public boolean delImg(SecondhandBuyPicture s) {
		daoPic.deleteById(s.getImageId());
		return true;
	}


	public boolean delbuyList(SecondhandBuylist sl) {
		dao.deleteById(sl.getBuylistId());
		return true;
	}


	@Override
	public boolean upDate(SecondhandBuylist sl) {
		dao.update(sl);
		return true;
	}




	
	
	
	
	
	
	


	
	
}