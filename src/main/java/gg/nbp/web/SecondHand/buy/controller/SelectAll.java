package gg.nbp.web.SecondHand.buy.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dto.BuyEvent;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/selectall")
public class SelectAll extends HttpServlet {
	private static final long serialVersionUID = 5250110307973476208L;
	int c ;
	
	@Autowired
	private SecondHandBuyService service ;
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8"); 
		
		List<SecondhandBuylist> list = service.selectAll();
		List<BuyEvent> listDTO = new ArrayList<>();
		for (SecondhandBuylist sl : list) {
			System.out.println("搜尋"+(++c));			
			List<SecondhandBuyPicture> imgList = service.selectimg(sl);
				
				for (SecondhandBuyPicture img : imgList) {
					File src = new File(img.getImage());
					String[] s = src.getName().split("/");
					img.setImage(s[s.length-1]);
				}
				sl.setImage(imgList);
				BuyEvent s = new BuyEvent(sl);
				System.out.println(s);
				listDTO.add(s);
				
		}
		
		if(listDTO.isEmpty())
			CommonUtil.writepojo2Json(resp, new OneString("查無資料"));
		else
		CommonUtil.writepojo2Json(resp, listDTO);
	}

}
