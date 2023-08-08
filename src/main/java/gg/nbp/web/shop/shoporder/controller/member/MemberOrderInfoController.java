package gg.nbp.web.shop.shoporder.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.MemberViewOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;


@RequestMapping("/OrderForMember")
@RestController
public class MemberOrderInfoController {
private static final long serialVersionUID = 1L;
    
	private Gson gson;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/listLength/{criteria}")
	public long getListLength(@PathVariable(required = false) Integer criteria, @SessionAttribute Member member) {

		if (criteria == null) {
			criteria = 1;
		}
		
   		Map<String, Integer> condition = new HashMap<>();
   		OrderSelection os = OrderSelection.values()[criteria - 1];
    	switch (os) {
		case ALL:
			break;
		case PAID:
			condition.put("payStatus", 2);
			break;
		case UNPAID:
			condition.put("payStatus", 1);
			break;
		case PAIDONEDELI:
			condition.put("payStatus", 3);
			break;
		case DELIVERD:
			condition.put("deliverState", 1);
			break;
		case UNDELI:
			condition.put("deliverState", 0);
			break;
		case ARRIVED:
			condition.put("deliverState", 2);
			break;
		case DONE:
			condition.put("orderStatus", 1);
			break;
		case CANCELED:
			condition.put("orderStatus", 2);
			break;
		case APPLYCAN:
			condition.put("orderStatus", 3);
			break;
		case APPLYRETURN:
			condition.put("orderStatus", 4);
			break;
		}
    	
    	condition.put("memberId", member.getMember_id());
    	return orderMasterService.countDataNum(condition);
    }
    	
	@GetMapping("/all/{setNum}/{criteria}")
	public List<MemberViewOrder> memberAll(@SessionAttribute Member member, @PathVariable Integer setNum, @PathVariable(required = false) Integer criteria) {
   		
   		int limit = 10;
   		Map<String, Integer> limitOffset = new TreeMap<>();
   		limitOffset.put("LIMIT", limit);
   		limitOffset.put("OFFSET", setNum);
    		
    	Map<String, Integer> whereCondition = new HashMap<>();
    		
   		whereCondition.put("memberId", member.getMember_id());
    	
   		if (criteria == null) {
   			criteria = 1;
   		}
   		
    	switch (criteria) {
		case 1:
			break;
		case 2:
			whereCondition.put("payStatus", 1);
			break;
		case 3:
			whereCondition.put("payStatus", 2);
			break;
		case 4:
			whereCondition.put("deliverState", 1);
			break;
		case 5:
			whereCondition.put("deliverState", 0);
			break;
		case 6:
			whereCondition.put("deliverState", 2);
			break;
		}

    	return orderMasterService.memberOrderList(whereCondition, limitOffset);
   	}
    	
	@GetMapping("/checkBonus")
	public Double checkNowBonus(@SessionAttribute Member member, RedirectAttributes redirect, @RequestHeader("referer") String refer) {
		if (member == null || member.isSuccessful() == false) {
			redirect.addAttribute("memberLocation", refer);
			ModelAndView mv = new ModelAndView("redirect:/notLogin");
			return null;
		} 
		
		return member.getBonus();
	}
		
    @GetMapping("/getOneProduct/{productId}")
	public TransOrderProduct getOneProduct(@SessionAttribute Member member,	@PathVariable Integer productId) {
    	
    	return orderMasterService.getOneProduct(productId);
    }
		
}