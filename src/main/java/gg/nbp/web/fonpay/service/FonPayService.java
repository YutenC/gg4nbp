package gg.nbp.web.fonpay.service;

import gg.nbp.web.fonpay.entity.Order;
import gg.nbp.web.fonpay.pojo.Body;

public interface FonPayService {
	/*
	 * 建立一筆交易
	 * 需要提供Order 以及其 Detail
	 * */
	public Body paymentCreateOrder(Order order);
	
	public Body paymentCancelOrder(String TransactionId);
	
	public Body paymentRefundOrder();
	
	public Body paymentQueryOrder(String TransactionId);
	
	public Body paymentRefundQueryOrder();
	
	public Body addPayment(Integer key,String secret,String merchantCode);
}
