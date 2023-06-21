package gg.nbp.web.SecondHand.buy.service;

import java.sql.SQLException;
import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;


public interface SecondHandBuyService extends CoreService {
	
	public boolean delete(Integer memberId , Integer eventId) throws SQLException;
	
	public SecondhandBuylist submit(SecondhandBuylist s);
	
	public SecondhandBuylist selectOne(Integer i);
	
	public List<BuyEvent> selectAll() throws SQLException ;
	
	public List<SecondhandBuyPicture> selectimg(SecondhandBuylist s) ;
	
	public boolean upDate(SecondhandBuylist sl);
	
	
	
}
