package gg.nbp.web.shop.shoporder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderDetail")
public class OrderDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderDetailService oDetailService;
	

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	
    	Gson gson = new Gson();
    	res.setCharacterEncoding("UTF-8");
    	res.setContentType("application/json");
    	PrintWriter pw = res.getWriter();
    	
    	HttpSession httpSession = req.getSession();
    	Member login = new Member();
    	login.setAccount("ReimuHakurei");
    	login.setPassword("HakureiShrine");
    	Member member = memberService.login(login);
    	httpSession.setAttribute("member", member);
    	
    	Member getMember = (Member)httpSession.getAttribute("member");
    	Integer memberId;
    	
    	if (getMember == null) {
    		res.sendRedirect("/Five_NBP.gg");
    		return;
    	} else {
    		memberId = getMember.getMember_id();
    	}
    	
    	if (req.getParameter("getMemberAll") != null) {
    		Collection<ResOrderDetail> rsODList = oDetailService.getMemberAllOrderDetail(memberId);
    		pw.println(gson.toJson(rsODList));
    		return;
    	}
    	
    	String orderStr = req.getParameter("getByOrderId");
    	if (orderStr != null) {
    		Integer orderId = Integer.valueOf(orderStr);
    		List<TransOrderProduct> trOPList = oDetailService.getOrderDetailByOrderId(orderId);
    		pw.println(gson.toJson(trOPList));
    		return;
    	}
    	
	}
}


//@RestController
//@RequestMapping("/OrderDetail")
//public class OrderDetailController {
//	private static final long serialVersionUID = 1L;
//    
//	@Autowired
//	private MemberService memberService;
//	
//	@Autowired
//	private OrderDetailService oDetailService;
//	
//	@GetMapping("/getMemberAll")
//	public Collection<ResOrderDetail> getMemberAll(@SessionAttribute("member") Member member) {
//		return oDetailService.getMemberAllOrderDetail(member.getMember_id());
//	}
//	
//	@GetMapping("/getOne")
//	public List<TransOrderProduct> getOneByOrderId(@RequestParam Integer orderId) {
//		return oDetailService.getOrderDetailByOrderId(orderId);
//	}
//}