package gg.nbp.web.SecondHand.buy.dto;

import java.lang.reflect.Field;
import java.util.Date;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;




public class BuyEvent {
	private Integer eventId ;
	private String memberName ;
	private String productName;
	private String type;
	private String content;
	private Integer estimate;
	private Integer price;
	private Date confirmTime;
	private String payState;
	private String approvalState;
	private Date applyTime;
	private String applicantBankNumber;
	
	
	public BuyEvent(SecondhandBuylist bs) {
		Field[] fields = BuyEvent.class.getDeclaredFields();
		eventId = bs.getBuylistId();
		memberName = "asdasd"; // 呼叫member_service 來查詢會員名字
		productName = bs.getProductName();
		content = bs.getContent();
		estimate = bs.getEstimate();
		price = bs.getPrice();
		
	}
	public BuyEvent() {
		
	}
	

}
