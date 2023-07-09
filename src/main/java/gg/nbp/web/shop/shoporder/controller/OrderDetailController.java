package gg.nbp.web.shop.shoporder.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	private Gson gson;
	
	public OrderDetailController() {
		this.gson = new Gson();
	}

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	
    	res.setCharacterEncoding("UTF-8");
    	res.setContentType("application/json");
    	PrintWriter pw = res.getWriter();
    	
    	HttpSession httpSession = req.getSession();
    	Member login = new Member();
    	login.setAccount("Black");
    	login.setPassword("fcea920f7412b5da7be0cf42b8c93759");
    	Member member = memberService.login(login);
    	httpSession.setAttribute("member", member);
    	
    	Member getMember = (Member)httpSession.getAttribute("member");
    	Integer memberId;
    	
    	if (getMember == null) {
    		res.sendRedirect("/gg4nbp");
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

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
    	String comment = req.getParameter("comment");
    	if (comment != null) {
    		Reader rd =  req.getReader();
			BufferedReader brd = new BufferedReader(rd);
			String reqStr = brd.readLine();
			reqStr = reqStr.substring(reqStr.indexOf(":") + 1, reqStr.length());
			
			System.out.println(reqStr);
			String[] elms = reqStr.split(",");
			for (String elm : elms) {
				System.out.println(elm);
			}
			
			Integer orderId = Integer.valueOf(elms[0].split(":")[1]);
			Integer productId = Integer.valueOf(elms[1].split(":")[1]);
			String starStr = elms[2].split(":")[1];
			Integer starNum = Integer.valueOf(starStr.substring(1, starStr.length() - 1));
			String commentStr = elms[3].split(":")[1];
			String commentContent = commentStr.substring(1, commentStr.length() - 3);
			
			System.out.println(orderId);
			System.out.println(productId);
			System.out.println(starNum);
			System.out.println(commentContent);
			
			pw.println(oDetailService.commentProduct(orderId, productId, starNum, commentContent));
			
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