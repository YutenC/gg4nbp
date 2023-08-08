package gg.nbp.web.shop.shoporder.controller.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.ResOrderMaster;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

@RestController
@RequestMapping("/checkout")
public class CheckOutOrderController {
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private Gson gson;

	@PostMapping
	public ResOrderMaster checkOut(@SessionAttribute Member member, RedirectAttributes redirect, @RequestHeader("referer") String refer,
									@RequestBody String jsonStr, @RequestParam String payment, @RequestParam String deliver, @RequestParam String discountRadio,
									@RequestParam String couponCode, @RequestParam String bonus) {
		JsonArray jsonArr = gson.fromJson(jsonStr.substring(jsonStr.indexOf(":")+1, jsonStr.length()-1), JsonArray.class);
		List<TransOrderProduct> transObj = gson.fromJson(jsonArr.get(0).getAsJsonObject().get("transObj"), new TypeToken<List<TransOrderProduct>>() {});
		JsonObject card = gson.fromJson(jsonArr.get(1).getAsJsonObject().get("card"), JsonObject.class);
		JsonObject address = gson.fromJson(jsonArr.get(2).getAsJsonObject().get("address"), JsonObject.class);
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
		
		return orderMasterService.getOrderResult(insertOk);
		
	}
	
}
