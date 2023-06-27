package gg.nbp.web.Manager.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Manager.service.ManagerService;
import gg.nbp.web.power.entity.Power;
import gg.nbp.web.power.service.PowerService;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;
import gg.nbp.web.power_of_manager.service.Power_of_ManagerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/manager_logining")
public class ManagerLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	ManagerService service ;
	
	@Autowired
	Power_of_ManagerService pomService;
	
	@Autowired
	PowerService pService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 從請求中獲取JSON數據
	    BufferedReader reader = request.getReader();
	    StringBuilder jsonBody = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        jsonBody.append(line);
	    }
	    reader.close();
	    
	    // 解析JSON數據
	    JsonObject jsonObject = JsonParser.parseString(jsonBody.toString()).getAsJsonObject();
	    
	    String manager_account= jsonObject.get("manager_account").getAsString();
	    String manager_password= jsonObject.get("manager_password").getAsString();
	    
	    Manager manager= new Manager();
	    manager.setAccount(manager_account);
	    manager.setPassword(manager_password);
	    
	    // 在這裡執行相應的業務邏輯，例如將數據保存到數據庫中
	    manager= service.login(manager);
	    
	    if (manager.isSuccessful()) {
			if (request.getSession(false) != null) {
				request.changeSessionId();
			}
			
			final int loggedId= manager.getManager_id();
			List<Power_of_Manager> pomListAll= pomService.findAll();
			List<Power_of_Manager> loggedPomList = pomListAll.stream()
                    .filter(pom -> (int)pom.getManager_id()== loggedId)
                    .collect(Collectors.toList());
			List<Power> powerList= pService.findAll();
			
			final HttpSession session = request.getSession();
			session.setAttribute("manager_loggedin", true);
			session.setAttribute("manager", manager);
			session.setAttribute("loggedPomList", loggedPomList);
			session.setAttribute("powerList", powerList);
		}
	    
	    // 創建回應JSON數據
	    JsonObject responseJson = new JsonObject();
	    responseJson.addProperty("successful", manager.isSuccessful()); // 設置成功標誌，根據實際情況設置
	    responseJson.addProperty("redirectUrl", (String) request.getSession().getAttribute("location")); // 設置重導的網址
	    
	    responseJson.addProperty("manager_id", manager.getManager_id());
	    responseJson.addProperty("account", manager.getAccount());
	    
	    
	    // 設置回應的Content-Type為application/json
	    response.setContentType("application/json");
	    
	    // 發送回應
	    PrintWriter writer = response.getWriter();
	    writer.println(responseJson.toString());
	    writer.close();
	}

}
