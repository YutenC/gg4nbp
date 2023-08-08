package gg.nbp.web.shop.shoporder.controller.manager;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;

@RequestMapping("/EditOrderForManager")
@RestController
public class ManagerEditOrderController {
private static final long serialVersionUID = 1L;
    
	private MemberService memberService;
	
	private OrderMasterService orderMasterService;
	
	private NoticeService noticeService;

	@Autowired
	public ManagerEditOrderController(MemberService ms, OrderMasterService os, NoticeService ns) {
		this.memberService = ms;
		this.orderMasterService = os;
		this.noticeService = ns;
	}
	
	@PostMapping("/update")
	public void updateOrder(@RequestBody OrderMaster om, @SessionAttribute Member member) {
		orderMasterService.updateFromManager(om);
		
		Notice notice = new Notice();
		notice.setMember_id(member.getMember_id());
		Format sfm1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		notice.setNotice_value("會員 " + member.getNick() + " 您已於" + sfm1.format(new Date()) + "完成取消，訂單編號" + om.getOrderId());
		
		noticeService.addNotice(notice);
		return;
	}
		
}
