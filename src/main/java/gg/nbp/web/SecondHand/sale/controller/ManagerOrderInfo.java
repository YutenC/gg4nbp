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

@WebServlet("/manager/sh_orderInfo")
public class ManagerOrderInfo extends HttpServlet {

//    member
//    product
//    order

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


        SecondhandOrder data = CommonUtil.json2pojo(req, SecondhandOrder.class);
        productId = data.getProductId();
        orderId = data.getOrderId();

        System.out.println(productId);
        System.out.println(orderId);

        SecondhandProduct shp = PROSERVICE.selectOne(productId);
        SecondhandOrder od = ODSERVICE.selectOne(orderId);
        int memberId = od.getMemberId();

        Member mem = MEMSERVICE.selectMember(memberId);

        Object[] objects = {mem, shp, od};

        CommonUtil.writepojo2Json(resp, objects);

    }
}
