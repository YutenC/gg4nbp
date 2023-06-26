package gg.nbp.web.SecondHand.buy.controller;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = -3236355283493258651L;
	@Autowired
	private SecondHandBuyService service ;
	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			
			BuyEvent be = CommonUtil.json2pojo(req,BuyEvent.class);
			CommonUtil.writepojo2Json(resp, service.update(be));
		} catch (Exception e) {
			CommonUtil.writepojo2Json(resp, new OneString("更新失敗"));
		}
		
		
	}
	
}
