package gg.nbp.ecpay.payment.integration.controller.ecpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import gg.nbp.ecpay.payment.integration.service.EcpayService;
import gg.nbp.web.Member.entity.Member;

@Controller
@RequestMapping("/SpEcpay")
public class SpEcpayController {
	
	@Autowired
	EcpayService ecpayService;
	
	@PostMapping
	public String ecpayCheckOut (@RequestParam Integer orderId, @SessionAttribute("member") Member member) {
		if (member == null) {
			return "redirect:/Five_NBP.gg";
		}
		
		return ecpayService.ecpayCheckout(orderId); 
	}
	
}