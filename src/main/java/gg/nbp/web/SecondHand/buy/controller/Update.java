package gg.nbp.web.SecondHand.buy.controller;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = -3236355283493258651L;
	@Autowired
	private SecondHandBuyService service ;
	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		SecondhandBuylist sl = CommonUtil.json2pojo(req , SecondhandBuylist.class);
		System.out.println(sl.getBuylistId());
		
		SecondhandBuylist sla = service.selectOne(sl.getBuylistId());
		sla.setProductName(sl.getProductName());
		sla.setType(sl.getType());
		sla.setContent(sl.getContent());
		sla.setEstimate(sl.getEstimate());
		sla.setPrice(sl.getPrice());
		sla.setPayState(sl.getPayState());
		sla.setApplicantBankNumber(sl.getApplicantBankNumber());
		
		System.out.println(sla);
		
		
		
		service.upDate(sla);
		
		OneString secc = new OneString();
		secc.setStr("修改成功");
		CommonUtil.writepojo2Json(resp, secc);
		
		
	}
	
}
