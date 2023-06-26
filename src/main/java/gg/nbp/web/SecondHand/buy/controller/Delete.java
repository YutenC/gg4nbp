package gg.nbp.web.SecondHand.buy.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/delete")
public class Delete extends HttpServlet  {
	private static final long serialVersionUID = -1806851938916882849L;

	@Autowired
	private SecondHandBuyService service ;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); 
		resp.setCharacterEncoding("UTF-8"); 
		
		
		String id = CommonUtil.json2pojo(req, OneString.class).getStr();
		try {
			/*******************************************************
			 * 第一個參數是發出請求人的ID : 之後從session中拿出
			 * 第二個參數為事件ID
			 *******************************************************/
			service.delete(null, Integer.parseInt(id));
			
			CommonUtil.writepojo2Json(resp, new OneString("刪除成功"));
		}catch (Exception e) {
			CommonUtil.writepojo2Json(resp, new OneString("刪除失敗"));
		}
		
	}

}
