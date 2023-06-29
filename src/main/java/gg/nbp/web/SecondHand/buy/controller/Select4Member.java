package gg.nbp.web.SecondHand.buy.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import gg.nbp.web.SecondHand.buy.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/member/select")
public class Select4Member extends HttpServlet {
	private static final long serialVersionUID = -4362073464223691043L;
	

	@Autowired
	private SecondHandBuyService service ;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		Boolean isLogin = (Boolean)session.getAttribute("isLogin");
		Member member = (Member)session.getAttribute("member");
		if(Boolean.TRUE.equals(isLogin)) {
			String str = CommonUtil.json2pojo(req, OneString.class).getStr();
			
			try {
				str = str.trim();
				CommonUtil.writepojo2Json(resp, service.searchByName(str, member));
			} catch (Exception e) {
				// 代表搜尋的字串查無結果
//				e.printStackTrace();
					CommonUtil.writepojo2Json(resp, new OneString("查無結果"));
			}
		}else
			CommonUtil.writepojo2Json(resp, new OneString("請登入"));
		
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
			/* 主檔名 */
		String imgName = req.getParameter("imgname");
		
		try {
			/* 副檔名 */
			resp.setContentType("image/gif");
		} catch (Exception e) {
			/* 沒有副檔名 */
			resp.setCharacterEncoding("UTF-8");
			CommonUtil.writepojo2Json(resp, new OneString("無效檔案"));
		}
		
		File src = new File(Constant.SAVE_URL + imgName);
		
		
		
		try (ServletOutputStream out = resp.getOutputStream();BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))){
			out.write(bis.readAllBytes());
		} catch (Exception e) {
//		e.printStackTrace();
		}
		
	}

}
