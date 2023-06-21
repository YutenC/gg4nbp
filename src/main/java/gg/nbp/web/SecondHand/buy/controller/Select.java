package gg.nbp.web.SecondHand.buy.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import gg.nbp.web.SecondHand.buy.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/select")
public class Select extends HttpServlet {
	private static final long serialVersionUID = -4362073464223691043L;
	

	@Autowired
	private SecondHandBuyService service ;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String str = CommonUtil.json2pojo(req, OneString.class).getStr();
		
		try {
			Integer id = Integer.parseInt(str);
			CommonUtil.writepojo2Json(resp, service.searchById(id));
		} catch (Exception e) {
			// 代表搜尋的字串不是數字 或是 數字查無結果
			try {
				CommonUtil.writepojo2Json(resp, service.searchByName(str));
			} catch (Exception ee) {
				CommonUtil.writepojo2Json(resp, new OneString("查無結果"));
			}
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		/* 主檔名 */
		String imgName = req.getParameter("imgname");
		
		try {
			/* 副檔名 */
			resp.setContentType("image/gif");
		} catch (Exception e) {
			// 沒有副檔名
			resp.setCharacterEncoding("UTF-8");
			CommonUtil.writepojo2Json(resp, new OneString("無效檔案"));
		}
		
		File src = new File(Constant.SAVE_URL + imgName);
		
		
		
		try (ServletOutputStream out = resp.getOutputStream();BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))){
			out.write(bis.readAllBytes());
		} catch (Exception e) {
		e.printStackTrace();
		}
		
	}

}
