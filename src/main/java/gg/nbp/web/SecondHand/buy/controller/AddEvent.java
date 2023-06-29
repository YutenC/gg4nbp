package gg.nbp.web.SecondHand.buy.controller;


import static gg.nbp.core.util.CommonUtil.json2pojo;
import static gg.nbp.core.util.CommonUtil.writepojo2Json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import gg.nbp.web.SecondHand.buy.util.Toolbox;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/secondhand/addEvent")
public class AddEvent extends HttpServlet  {
	private static final long serialVersionUID = -4669764916210514485L;

	@Autowired
	private SecondHandBuyService service ;
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); 
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		if(member == null) {
			resp.sendRedirect(req.getContextPath()+"/member_login.html");
			return;
		}
		
		
		
		try {
			SecondhandBuylist buylist = json2pojo(req, SecondhandBuylist.class);
			
			/* 對資料驗證是否為空值，如果空值丟出例外直接跳到catch*/
			String[] checks = {	buylist.getProductName(),
								buylist.getContent(),
								buylist.getApplicantBankNumber()};
			if (Toolbox.isEmpty4Strings(checks)) 
				throw new IOException() ;
			
			/* 回傳申請結果 */
			writepojo2Json(resp, service.submit( buylist,member.getMember_id()));
						
		} catch (Exception e) {
			/* 回傳申請失敗 */
			e.printStackTrace();
			
			writepojo2Json(resp, new OneString("申請失敗"));
		}
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); 
		resp.setCharacterEncoding("UTF-8");
		final HttpSession session = req.getSession();
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		
		if(isLogin == null || isLogin == false) {
			resp.sendRedirect(req.getContextPath()+"/member_login.html");
			
		}
		else {
			writepojo2Json(resp, new OneString("已登入"));
			
		}
		
		
		
		
	}
	
	
	

}
