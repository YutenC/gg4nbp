package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.impl.MemberServiceimpl;
import gg.nbp.web.Member.util.MemberCommonUitl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandOrderServiceImpl;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/member/sh_orderInfo")
public class OrderInfo extends HttpServlet {

//    member
//    product
//    order

/**
	 * 
	 */
	private static final long serialVersionUID = -5999772871458031737L;
int productId = 0;
int orderId = 0;

@Autowired
private MemberServiceimpl MEMSERVICE;

@Autowired
private SecondhandOrderServiceImpl ODSERVICE;

@Autowired
private SecondhandProductServiceImpl PROSERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member member = MemberCommonUitl.getMemberSession(req,"member");
        int memberId = member.getMember_id();

        SecondhandOrder data = CommonUtil.json2pojo(req, SecondhandOrder.class);
        productId = data.getProductId();
        orderId = data.getOrderId();

        System.out.println(productId);
        System.out.println(orderId);

        Member mem = MEMSERVICE.selectMember(memberId);
        SecondhandProduct shp = PROSERVICE.selectOne(productId);
        SecondhandOrder od = ODSERVICE.selectOne(orderId);

        Object[] objects = {mem, shp, od};

        CommonUtil.writepojo2Json(resp, objects);

    }
}
