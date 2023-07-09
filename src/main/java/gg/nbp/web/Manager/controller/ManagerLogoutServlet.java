package gg.nbp.web.Manager.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;

import gg.nbp.web.Manager.service.ManagerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/logout")
public class ManagerLogoutServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Autowired
	ManagerService service ;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Manager manager = (Manager)request.getSession().getAttribute("manager");
		
		request.getSession().removeAttribute("manager_loggedin");
		request.getSession().removeAttribute("manager");
		request.getSession().removeAttribute("loggedPomList");
		request.getSession().removeAttribute("powerList");
		
		Object managerLoggedIn = request.getSession().getAttribute("manager_loggedin");
		
		// 創建回應JSON數據
	    JsonObject responseJson = new JsonObject();
	    responseJson.addProperty("successful", false);
	    
		if (managerLoggedIn == null) {
			responseJson.addProperty("successful", true); // 設置成功標誌，根據實際情況設置
		}
		
//	     設置回應的Content-Type為application/json    
	    response.setContentType("application/json");
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
	    
	 // 發送回應
	    PrintWriter writer = response.getWriter();
	    writer.println(responseJson.toString());
	    writer.close();
		
	}

}
