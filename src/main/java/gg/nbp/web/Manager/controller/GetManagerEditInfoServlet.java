package gg.nbp.web.Manager.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Manager.service.ManagerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/getManagerEditInfo")
public class GetManagerEditInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	ManagerService service ;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
            // 讀取請求的內容
            BufferedReader reader = request.getReader();
            StringBuilder jsonBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
            reader.close();

            // 將 JSON 字串轉換為對應的 Java 物件
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonBody.toString(), JsonObject.class);
            int id = jsonObject.get("id").getAsInt();

            // 在此處根據 ID 做相應的處理
            // ...
            
            Manager manager= service.selectManager(id);
            
            String json = gson.toJson(manager);

            // 將回應返回給前端
            JsonObject responseJson = new JsonObject();
            responseJson.add("manager", JsonParser.parseString(json));
            // 在 responseJson 中設置相應的資料
            // ...

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(responseJson.toString());
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            // 處理例外情況
        }
		
		
	}

}
