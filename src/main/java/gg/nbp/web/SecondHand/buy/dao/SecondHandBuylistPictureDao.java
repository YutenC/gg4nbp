package gg.nbp.web.SecondHand.buy.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;


public interface SecondHandBuylistPictureDao extends CoreDao<SecondhandBuyPicture, Integer> {
	public List<SecondhandBuyPicture> selectBylistId(Integer  i);
}
