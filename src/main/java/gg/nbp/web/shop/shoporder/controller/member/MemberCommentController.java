package gg.nbp.web.shop.shoporder.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import gg.nbp.web.shop.shoporder.service.OrderDetailService;

@RestController
public class MemberCommentController {

	@Autowired
	private OrderDetailService orderDetailService;
	
	@PostMapping("/LeaveComment")
	public boolean commentProduct(@RequestBody JsonObject json) {
		Integer orderId = json.get("orderId").getAsInt();
		Integer productId = json.get("productId").getAsInt();
		Integer starNum = json.get("star").getAsInt();
		String comment = json.get("comment").getAsString();
		return orderDetailService.commentProduct(orderId, productId, starNum, comment); 
	}
	
	
}
