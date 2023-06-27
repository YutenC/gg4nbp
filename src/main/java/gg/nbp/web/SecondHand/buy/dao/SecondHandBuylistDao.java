package gg.nbp.web.SecondHand.buy.dao;


import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;



public interface SecondHandBuylistDao extends CoreDao<SecondhandBuylist, Integer> {

	public List<SecondhandBuylist> selectByName(String Name);
	
	public List<SecondhandBuylist> selectByMemberId(Integer id);
	
	public List<SecondhandBuylist> selectByName4Member(String Name , Integer id);
}
