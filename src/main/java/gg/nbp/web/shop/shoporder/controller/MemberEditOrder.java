package gg.nbp.web.shop.shoporder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;

@RestController
@RequestMapping("/EditOrderFromMember")
public class MemberEditOrder {
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	public void editOrderFromMember(@SessionAttribute Member member, RedirectAttributes redirect, @RequestHeader("referer") String refer,
									@RequestBody OrderMaster fromMember) {
		if (member == null || member.isSuccessful() == false) {
			redirect.addAttribute("memberLocation", refer);
			ModelAndView mv = new ModelAndView("redirect:/notLogin");
			return;
		} 
		
		orderMasterService.updateFromMember(fromMember);
		return;
	}

}
