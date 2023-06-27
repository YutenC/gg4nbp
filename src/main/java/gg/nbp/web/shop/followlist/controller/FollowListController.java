package gg.nbp.web.shop.followlist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.followlist.service.FollowListService;
import gg.nbp.web.shop.followlist.util.ResFollowList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/FollowList")
public class FollowListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@Autowired
	FollowListService followListService;
	
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	
    	Gson gson = new Gson();
    	res.setCharacterEncoding("UTF-8");
    	res.setContentType("application/json");
    	PrintWriter pw = res.getWriter();
    	
    	HttpSession httpSession = req.getSession();
    	Member member = new Member();
    	member.setMember_id(1);
    	httpSession.setAttribute("member", member);
    	
    	Member getMember = (Member)httpSession.getAttribute("member");
    	Integer memberId;
    	
    	if (getMember == null) {
    		res.sendRedirect("/Five_NBP.gg");
    		return;
    	} else {
    		memberId = getMember.getMember_id();
    	}
    	
    	if (req.getParameter("getAll") != null) {
    		List<ResFollowList> rsFList = followListService.getAllFollowProduct(memberId);
    		pw.println(gson.toJson(rsFList));
    		return;
    	}
    	
    	if (req.getParameter("delPdId") != null) {
    		Integer delPdId = Integer.valueOf(req.getParameter("delPdId"));
        	boolean result = followListService.deleteFollowList(memberId, delPdId);
        	pw.print(gson.toJson(result));
        	return;
    	}
    	
    	if (req.getParameter("addOne") != null) {
    		Integer addId = Integer.valueOf(req.getParameter("addOne"));
    		boolean result = followListService.addFollowList(memberId, addId);
    		pw.println(result);
    		return;
    	}
	}

}

//@RestController
//@RequestMapping("/FollowList")
//public class FollowListController{
//    
//	@Autowired
//	FollowListService followListService;
//	
//	@GetMapping("/getAll")
//	public List<ResFollowList> getAll (@SessionAttribute("member") Member member) {
//	  return followListService.getAllFollowProduct(member.getMember_id());
//	}
//	
//	public boolean deleteOne(@RequestParam Integer productId, @SessionAttribute("member") Member member) {
//		return followListService.deleteFollowList(member.getMember_id(), productId);
//	}
//
//	public boolean addOne(@RequestParam Integer productId, @SessionAttribute("member") Member member) {
//		return followListService.addFollowList(memberId, addId);		
//	}
//	
//}