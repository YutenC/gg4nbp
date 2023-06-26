package gg.nbp.web.Manager.controller;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/manager/manager_list")
public class ManagerListServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Autowired
	ManagerService service ;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		List<Manager> managerList = service.findAll();
		
		// 使用 Gson 將 managerList 轉換為 JSON 字串
        Gson gson = new Gson();
        String json = gson.toJson(managerList);
        
        // 建立一個包含 managerList 屬性的物件
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("managerList", JsonParser.parseString(json));

        // 設置回應的 Content-Type 為 application/json
        resp.setContentType("application/json");

        // 將 JSON 字串寫入回應中
        resp.getWriter().write(jsonObject.toString());
		
	}
	
}
