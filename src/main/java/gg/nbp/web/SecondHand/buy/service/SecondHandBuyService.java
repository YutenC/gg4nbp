package gg.nbp.web.SecondHand.buy.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;


public interface SecondHandBuyService extends CoreService {
	public boolean insert(SecondhandBuylist s);
	
	public boolean insertimg(SecondhandBuyPicture img , Integer id);
	
	public SecondhandBuylist selectOne(Integer i);
	
	public List<SecondhandBuylist> selectAll() ;
	
	public List<SecondhandBuyPicture> selectimg(SecondhandBuylist s) ;
	
	public boolean delImg(SecondhandBuyPicture s);
	
	public boolean delbuyList(SecondhandBuylist sl);
	
	public boolean upDate(SecondhandBuylist sl);
	
	
	
}
