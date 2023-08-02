package gg.nbp.web.fonpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gg.nbp.web.fonpay.pojo.Body;
import gg.nbp.web.fonpay.service.FonPayService;

@Controller
@RequestMapping("/fonpay")
@CrossOrigin
public class FonPayController {
	@Autowired
	private FonPayService fonPayService;
	/*
	 * 資料庫需要一個Store的Table : Store
	 * Store_Id : int,PK,AI
	 * Store_Name :VARCHAR(??)
	 * 其他還沒想到
	 * 
	 * 還需要一個儲存金流的Table  : Payment
	 * Payment_Id : int,PK,AI
	 * key : int
	 * secret : VARCHAR(20) 
	 * merchantCode : VARCHAR(10) 
	 * 
	 * 以及Payment跟Store的關係Table : Payment_Detail
	 * Store_Id : int,PK,FK
	 * Payment_Id : int,PK,FK
	 * 
	 * 然後需要一個Order的Table : Order
	 * Order_Id : int,PK,AI
	 * Store_Id : int,FK
	 * member_Id : int,FK
	 * paymentNo : VARCHAR(??)
	 * itemName : TEXT or VARCHAR(??)
	 * totalPrice : int
	 * refundPrice : int
	 * refundNo : VARCHAR(??)
	 * paymentTransactionId : VARCHAR(16)
	 * 
	 * 然後需要一個Order_Detail的Table : Order_Detail
	 * Detail_Id : int,PK,AI
	 * Order_Id : int,FK
	 * itemName : TEXT or VARCHAR(??)
	 * itemQuantity : int
	 * 
	 * 
	 * 
	 * */

	@PostMapping("/paymentCreateOrder")
	@ResponseBody
	public Body paymentCreateOrder() {
		Body res = new Body();
		return res ;
	}
	
	@PostMapping("/paymentCancelOrder")
	@ResponseBody
	public Body paymentCancelOrder() {
		Body res = new Body();
		return res ;
	}
	
	@PostMapping("/paymentRefundOrder")
	@ResponseBody
	public Body paymentRefundOrder() {
		Body res = new Body();
		return res ;
	}
	
	@PostMapping("/paymentQueryOrder")
	public Body paymentQueryOrder() {
		Body res = new Body();
		return res ;
	}
	
	@PostMapping("/paymentRefundQueryOrder")
	public Body paymentRefundQueryOrder() {
		Body res = new Body();
		return res ;
	}
	
	@PostMapping("/addPayment")
	@ResponseBody
	public Body addPayment() {
		Body res = new Body();
		return res ;
	}
	
	
}
