package gg.nbp.web.shop.shoporder.controller.purchase;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;

@RestController
@RequestMapping("/PurchaseLog")
public class PurchaseLogController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@GetMapping("/all")
	public Collection<ResOrderDetail> getPastPurchase(@SessionAttribute Member member) {
		return orderDetailService.getMemberAllOrderDetail(member.getMember_id()); 
	}
}
