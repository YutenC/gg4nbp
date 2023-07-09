package gg.nbp.web.SecondHand.sale.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.MemberCommonUitl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandOrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/shp_allOrder")
public class OrderMemViewServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5810670870595375656L;

	@Autowired
    private SecondhandOrderServiceImpl SERVICE;

//    @Autowired
//    private SecondhandProductServiceImpl PROSERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member member = MemberCommonUitl.getMemberSession(req,"member");

        // 取會員訂單資料
        int memberId = member.getMember_id();
        List<SecondhandOrder> orderList = SERVICE.selectMem(memberId);

        // 取訂單的商品名稱
//        List<String> shpNameList = new ArrayList<>();
//
//        for(SecondhandOrder od: orderList){
//            int productId = od.getProductId();
//            SecondhandProduct shp = PROSERVICE.selectOne(productId);
//            String shpName = shp.getName();
//            shpNameList.add(productId, shpName);
//        }
//
//        List<Object> objects = new ArrayList<>();
//        objects.add(orderList);
//        objects.add(shpNameList);
//
//        CommonUtil.writepojo2Json(resp, objects);

        CommonUtil.writepojo2Json(resp, orderList);



    }


}
