package gg.nbp.web.SecondHand.buy.controller;


import static gg.nbp.core.util.CommonUtil.json2pojo;
import static gg.nbp.core.util.CommonUtil.writepojo2Json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/secondhand/addbuyevent")
public class AddBuyEvent extends HttpServlet  {
	private static final long serialVersionUID = -4669764916210514485L;

	@Autowired
	private SecondHandBuyService service ;
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); 
		int buylistId = 0 ; 
		SecondhandBuylist buylist = null ;
		
		
		try {
			buylist = json2pojo(req, SecondhandBuylist.class);
			
			/* 對資料驗證是否為空值 */
			if (buylist.getProductName().isEmpty() || buylist.getContent().isEmpty() || buylist.getApplicantBankNumber().isEmpty()) 
				throw new IOException() ;
			
			service.insert(buylist);
			
			buylist.setSuccessful(true);
			
			buylistId = buylist.getBuylistId();
			
//			if (buylist.isSuccessful()) {
//				if (req.getSession(false) != null) {
//					req.changeSessionId();
//				}
//				final HttpSession session = req.getSession();
//				session.setAttribute("buylistId", buylistId);
//			}
			
		} catch (Exception e) {
			buylist = new SecondhandBuylist();
			buylist.setSuccessful(false);
		}
		
		
		
		try {
			
			for (SecondhandBuyPicture img : buylist.getImage()) 
				service.insertimg((SecondhandBuyPicture)img, buylistId); 
			
		} catch (Exception e) {
		}
		writepojo2Json(resp, buylist);
	}
	
	
	

}
