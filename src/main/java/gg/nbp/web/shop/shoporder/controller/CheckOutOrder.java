package gg.nbp.web.shop.shoporder.controller;

import java.io.BufferedReader;
import java.io.Reader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import gg.nbp.ecpay.payment.integration.service.EcpayService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.ResOrderMaster;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

@RestController
@RequestMapping("/checkout")
public class CheckOutOrder {
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	@Autowired
	private EcpayService ecpayService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private Gson gson;

	@PostMapping
	public String checkOut(@SessionAttribute Member member, RedirectAttributes redirect, @RequestHeader("referer") String refer,
									@RequestBody ObjectNode json, @RequestParam String payment, @RequestParam String deliver, @RequestParam String discountRadio,
									@RequestParam String couponCode, @RequestParam String bonus) {
		
		if (member == null || member.isSuccessful() == false) {
			redirect.addAttribute("memberLocation", refer);
			ModelAndView mv = new ModelAndView("redirect:/notLogin");
			return null;
		} 
		
		List<TransOrderProduct> transObj = gson.fromJson(json.get("transObj").asText(), new TypeToken<List<TransOrderProduct>>() {});
		
		JsonObject card = ;
		JsonObject address = ;
		List<TransOrderProduct> purchaseProducts = new ArrayList<>();
		for (TransOrderProduct trOdPd : transObj) {
			if (trOdPd.isChecked() == true) {
				purchaseProducts.add(trOdPd);
			}
		}
		
		OrderMaster om = orderMasterService.createNewOrderMaster(transObj, card, address, member, payment,
																			deliver, discountRadio, couponCode, bonus);
		
		orderMasterService.establishNewOrder(om, purchaseProducts, member);
		
		OrderMaster insertOk = orderMasterService.getOne(om.getOrderId());
		
		// 寄送成功下單通知
		Notice notice = new Notice();
		notice.setMember_id(member.getMember_id());
		notice.setNotice_value("會員 " + member.getNick() + " 您已於" + om.getCommitDate() + "完成下單，訂單編號" + om.getOrderId());
		
		noticeService.addNotice(notice);
		
		return "redirect:" + ecpayService.ecpayCheckout(insertOk.getOrderId());
		
	}
	
}
