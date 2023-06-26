package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager/sh_ordermanage")
public class BackSelectOrderServlet extends HttpServlet {

    @Autowired
    private SecondhandOrderService SERVICE;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<SecondhandOrder> orderList = SERVICE.selectAll();

        CommonUtil.writepojo2Json(resp, orderList);

    }
}
