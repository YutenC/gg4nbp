package gg.nbp.ecpay.payment.integration.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OrderResult")
public class RedirectToOrderResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RedirectToOrderResult() {
        super();
    }
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		res.setCharacterEncoding("UTF-8");
		res.sendRedirect("member/orderResult(Vue).html");
	}

}
