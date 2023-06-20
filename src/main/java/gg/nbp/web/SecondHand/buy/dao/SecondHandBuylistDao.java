package gg.nbp.web.SecondHand.buy.dao;


import java.util.Date;
import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;



public interface SecondHandBuylistDao extends CoreDao<SecondhandBuylist, Integer> {

	List<SecondhandBuylist> selectByTime(Date starttime , Date endtime);
}
