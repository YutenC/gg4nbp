package gg.nbp.web.SecondHand.buy.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;


public interface SecondHandBuylistPictureDao extends CoreDao<SecondhandBuyPicture, Integer> {
	public List<SecondhandBuyPicture> selectBylistId(Integer  i);
	
	public int update(BuyEvent be) ;
		
	
}
