package gg.nbp.ecpay.payment.integration.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import gg.nbp.ecpay.payment.integration.service.EcpayService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Ecpay")
public class EcpayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemberService memServ;
	@Autowired
	private EcpayService ecService;
	
	
       
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	
    	res.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html");
    	PrintWriter pw = res.getWriter();
    	
    	String order = req.getParameter("orderId");
    	if (order != null) {
    		Integer orderId = Integer.valueOf(order);
    		
    		pw.println(ecService.ecpayCheckout(orderId));
    	
    		return;
    	}
	}
    
}

//@Controller
//@RequestMapping("/Ecpay")
//public class EcpayController {
//	
//	@Autowired
//	EcpayService ecpayService;
//	
//	@PostMapping
//	public String ecpayCheckOut (@RequestParam Integer orderId, @SessionAttribute("member") Member member) {
//		if (member == null) {
//			return "redirect:/Five_NBP.gg";
//		}
//		
//		return ecpayService.ecpayCheckout(orderId); 
//	}
//	
//}

