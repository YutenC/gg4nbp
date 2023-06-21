package gg.nbp.web.SecondHand.buy.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
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
		String id = CommonUtil.json2pojo(req, OneString.class).getStr();
		try {
			SecondhandBuylist sl = service.selectOne(Integer.parseInt(id));
			List<SecondhandBuyPicture> imgList = service.selectimg(sl);

			for (SecondhandBuyPicture img : imgList) {
				File src = new File(img.getImage());
				String[] s = src.getName().split("/");
				img.setImage(s[s.length - 1]);
			}
			sl.setImage(imgList);
			CommonUtil.writepojo2Json(resp, new BuyEvent(sl));
		} catch (Exception e) {
			CommonUtil.writepojo2Json(resp, new OneString("查無結果"));
		}

	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		File src = new File(Constant.SAVE_URL + req.getParameter("imgname"));
		resp.setContentType("image/gif");
		try (ServletOutputStream out = resp.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))) {
			out.write(bis.readAllBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
