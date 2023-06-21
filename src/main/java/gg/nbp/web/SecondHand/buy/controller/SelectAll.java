package gg.nbp.web.SecondHand.buy.controller;


import java.io.IOException;
import java.util.List;

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


@WebServlet("/secondhand/selectall")
public class SelectAll extends HttpServlet {
	private static final long serialVersionUID = 5250110307973476208L;
	
	@Autowired
	private SecondHandBuyService service ;
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8"); 
		try {
			List<BuyEvent> listDTO =  service.selectAll();
			CommonUtil.writepojo2Json(resp, listDTO);
			
		} catch (Exception e) {
			CommonUtil.writepojo2Json(resp, new OneString("查無資料"));
		}
		
	}

}
