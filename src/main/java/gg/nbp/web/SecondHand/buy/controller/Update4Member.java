package gg.nbp.web.SecondHand.buy.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import gg.nbp.web.SecondHand.buy.util.Toolbox;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/update4Member")
public class Update4Member extends HttpServlet {
	private static final long serialVersionUID = 187427524176926579L;
	
	@Autowired
	private SecondHandBuyService service ;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = req.getSession();
		Boolean isLogin = (Boolean)session.getAttribute("isLogin");
		Member member = (Member)session.getAttribute("member");
		
		if(Boolean.TRUE.equals(isLogin)) {
			try {
				BuyEvent be = CommonUtil.json2pojo(req,BuyEvent.class);
				if(service.searchById(be.getEventId()).get(0).getApprovalState().equals("未完成")) {
					String[] checks = {	be.getProductName(),
										be.getContent(),
										be.getApplicantBankNumber()};
					if (Toolbox.isEmpty4Strings(checks)) 
						return;						
					
				}
				
				CommonUtil.writepojo2Json(resp, service.update4Mem(be,member));
			} 
			catch (SQLException sqle) {
				sqle.printStackTrace();
				CommonUtil.writepojo2Json(resp, new OneString("訂單已經完成"));
			}
			catch (NullPointerException nue) {
				nue.printStackTrace();
				CommonUtil.writepojo2Json(resp, new OneString("請等待管理員提供收購價"));
			}
			catch (Exception e) {
				e.printStackTrace();
				CommonUtil.writepojo2Json(resp, new OneString("更新失敗"));
			}
		}else {
			resp.sendRedirect(req.getContextPath()+"/member_login.html");
			session.setAttribute("memberLocation", req.getHeader("referer"));
		}
	}
}
