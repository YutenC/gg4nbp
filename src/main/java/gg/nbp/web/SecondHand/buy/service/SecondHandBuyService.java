package gg.nbp.web.SecondHand.buy.service;

import java.sql.SQLException;
import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;


public interface SecondHandBuyService extends CoreService {
	
	public boolean delete(Integer memberId , Integer eventId) throws SQLException;
	
	public BuyEvent submit(SecondhandBuylist sl, Integer id);
	
	public List<BuyEvent> searchById(Integer id);
	
	public List<BuyEvent> searchByName(String str) throws SQLException;
	
	public List<BuyEvent> searchAll() throws SQLException ;
	
	public BuyEvent update(SecondhandBuylist sl);
	
	
	
}
