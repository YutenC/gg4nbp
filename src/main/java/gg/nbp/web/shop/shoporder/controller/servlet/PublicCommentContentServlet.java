package gg.nbp.web.shop.shoporder.controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/publicComment")
public class PublicCommentContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OrderDetailService oDetailService;
	
	private Gson gson;
	
	public PublicCommentContentServlet( ) {
		this.gson = new Gson();
	}
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
		String getProductComments = req.getParameter("getProductComments");
    	if (getProductComments != null) {
    		Integer productId = Integer.valueOf(getProductComments);
    		List<TransOrderProduct> trlist = oDetailService.getCommentContentsByProductId(productId);
    		pw.println(gson.toJson(trlist));
    		return;
    	}
	}

}
