package gg.nbp.web.SecondHand.buy.service.impl;

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
	
	
	
	
	
	@Transactional
	@Override
	public boolean insert(SecondhandBuylist s) {
		
		s.setMemberId(1);			
		s.setPayState(0);			// 預設為 0
		s.setApprovalState("0");	// 預設為 0
		dao.insert(s);
		return true;
	}


	@Transactional
	@Override
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


	@Transactional
	@Override
	public boolean delImg(SecondhandBuyPicture s) {
		daoPic.deleteById(s.getImageId());
		return true;
	}


	@Transactional
	@Override
	public boolean delbuyList(SecondhandBuylist sl) {
		dao.deleteById(sl.getBuylistId());
		return true;
	}


	@Transactional
	@Override
	public boolean upDate(SecondhandBuylist sl) {
		dao.update(sl);
		return true;
	}
	
	
	
	
	
	


	
	
}