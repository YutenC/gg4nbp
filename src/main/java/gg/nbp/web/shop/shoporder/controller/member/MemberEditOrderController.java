package gg.nbp.web.shop.shoporder.controller.member;

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
public class MemberEditOrderController {
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	public void editOrderFromMember(@RequestBody OrderMaster fromMember) {
				
		orderMasterService.updateFromMember(fromMember);
	}

}
