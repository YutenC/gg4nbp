package gg.nbp.web.power_of_manager.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gg.nbp.web.power.entity.Power;
import gg.nbp.web.power.service.PowerService;
import gg.nbp.web.power.service.impl.PowerServiceImpl;
import gg.nbp.web.power_of_manager.entity.Power_of_Manager;
import gg.nbp.web.power_of_manager.service.Power_of_ManagerService;

@WebServlet("/manager/pom_add")
public class PoMAddServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	Power_of_ManagerService service;
	
	@Autowired
	PowerService pService;
	
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
//		傳manager_id 給servlet
//		傳一個checkedIdList<power_id> 給servlet 
	    JsonObject jsonObject = JsonParser.parseString(jsonBody.toString()).getAsJsonObject();
	    
	    Integer manager_id= jsonObject.get("manager_id").getAsInt();
	    
	    JsonArray checkedIdArray=  jsonObject.get("chekedIdList").getAsJsonArray();
	    List<Integer> checkedIdList = new ArrayList<>();
        for (int i = 0; i < checkedIdArray.size(); i++) {
            checkedIdList.add(checkedIdArray.get(i).getAsInt());
        }
	    
//    	servlet會先用 findAllId() 找出所有的power_id並做成list_allID <power_id>
        List<Power> powerList= pService.findAll();
        List<Integer> powerIdList = new ArrayList<>();
        for (Power power : powerList) {
            powerIdList.add(power.getPower_id());
        }
	    
	    
	    // 在這裡執行相應的業務邏輯，例如將數據保存到數據庫中
//    	for(id: list_allID)
//    	if (list_activeID.contain(id)){
//    		if(pom已有){
//    			nothing
//    		}else{
//    			insert/save (pom)
//    		}
//    	}else{
//    		if(pom已有){
//    			delete(pom)
//    		}else{
//    			nothing
//    		}
//    	}
	    
	    for (Integer power_id: powerIdList) {
	    	Power_of_Manager pom= new Power_of_Manager();
	    		    	
	    	Power_of_Manager.PK pk = new Power_of_Manager.PK();
	    	pk.manager_id = manager_id;
	    	pk.power_id = power_id;
	    	
	    	pom.setManager_id(pk.manager_id);
	    	pom.setPower_id(pk.power_id);
	    	
	    	
	    	if (checkedIdList.contains(power_id)) {
	    		service.savePower_of_Manager(pom);
	    	}else if (!(checkedIdList.contains(power_id)) && service.selectPower_of_Manager(pk)!= null) {
	    		service.deletePower_of_Manager(pk);
	    	}
	    }
        
        
        
        
	    
	    // 創建回應JSON數據
	    JsonObject responseJson = new JsonObject();
	    
	    
	    
	    responseJson.addProperty("successful", true); // 設置成功標誌，根據實際情況設置
	    responseJson.addProperty("redirectUrl", request.getContextPath() + "/manager/manager_list.html"); // 設置重導的網址
	    
	    // 設置回應的Content-Type為application/json
	    
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
	    
	    response.setContentType("application/json");
	    
	    // 發送回應
	    PrintWriter writer = response.getWriter();
	    writer.println(responseJson.toString());
	    writer.close();
	}
	
	
}
