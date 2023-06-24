package gg.nbp.web.ban.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.ban.entity.Ban;
import gg.nbp.web.ban.service.BanService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/manager/ban_readIn")
public class BanReadInServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Autowired
	BanService service;
	@Autowired
	MemberService mService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		// 從請求中獲取JSON數據
	    BufferedReader reader = req.getReader();
	    StringBuilder jsonBody = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        jsonBody.append(line);
	    }
	    reader.close();
	    
	    // 解析接收JSON數據
	    // get Session
	 	Ban ban= new Ban();
	    
	    JsonObject jsonObject = JsonParser.parseString(jsonBody.toString()).getAsJsonObject();
	    
	    HttpSession session= req.getSession();
	 	Manager manager= (Manager) session.getAttribute("manager");
	 	
	 	Member member = mService.selectMember(jsonObject.get("member_id").getAsInt());
	 	if (member== null) {
	 		//找不到該會員的話(基本不用寫? member_id直接前端鎖死)
	 	}
	 	
	    ban.setMember_id(member.getMember_id());
	    ban.setManager_id(manager.getManager_id());
	    ban.setBan_reason(jsonObject.get("ban_reason").getAsString());
	    ban.setBan_durationByDay(jsonObject.get("ban_durationByDay").getAsInt());
	    
	    
	    // 在這裡執行相應的業務邏輯，例如將數據保存到數據庫中
		// 寫入Ban
	    ban= service.createBanCase(ban);
	    // 對會員做停權處理
	    Integer originalVerState= 1;
	    
	    if (member.getMember_ver_state() !=2) {
	    	originalVerState= member.getMember_ver_state(); 
	    }
	    
	    
	    LocalDateTime now = LocalDateTime.now();
	    Date memberSuspend_deadline= member.getSuspend_deadline();
	    LocalDateTime suspend_deadline;
        if (memberSuspend_deadline != null) {
            suspend_deadline = memberSuspend_deadline.toLocalDate().atStartOfDay();
        } else {
            suspend_deadline = LocalDateTime.MIN; // 设置为最小值，表示无效的时间
        }
	    
	    if (now.isAfter(suspend_deadline)) {
	    	//已經過了停權時間or本來沒有停權，
//	    	該設定狀態為停權
//	    	停權結束時間為now+ ban_durationByHour
	    	suspend_deadline= now.plusDays(ban.getBan_durationByDay());    	
	    	if (now.isAfter(suspend_deadline)) {
	    		member.setMember_ver_state(originalVerState);
	    		member.setSuspend_deadline(null);
	    	}else {
	    		member.setMember_ver_state(2);
	    		member.setSuspend_deadline(Date.valueOf(suspend_deadline.toLocalDate()));
	    	}	    	
	    }else {
	    	// 停權中，該把ban_durationByHour加上去
//	    	停權結束時間為suspend_deadline+ ban_durationByHour
	    	suspend_deadline= suspend_deadline.plusDays(ban.getBan_durationByDay());
	    	if (now.isAfter(suspend_deadline)) {
	    		member.setMember_ver_state(originalVerState);
	    		member.setSuspend_deadline(null);
	    	}else {
	    		member.setMember_ver_state(2);
	    		member.setSuspend_deadline(Date.valueOf(suspend_deadline.toLocalDate()));
	    	}
	    }
	    member= mService.edit(member);
	    
	 // 創建回應JSON數據
	    JsonObject responseJson = new JsonObject();
	    responseJson.addProperty("successful", ban.isSuccessful()); // 設置成功標誌，根據實際情況設置
	    responseJson.addProperty("redirectUrl", req.getContextPath() + "/manager/manager_memList.html"); // 設置重導的網址
	    responseJson.addProperty("message", ban.getMessage()); //回傳訊息
	    responseJson.addProperty("member_id", member.getMember_id());
	    responseJson.addProperty("manager_id", manager.getManager_id());
	    
	    // 設置回應的Content-Type為application/json
	    resp.setContentType("application/json");
	    
	    // 發送回應
	    PrintWriter writer = resp.getWriter();
	    writer.println(responseJson.toString());
	    writer.close();
	    
		
	}
	
}