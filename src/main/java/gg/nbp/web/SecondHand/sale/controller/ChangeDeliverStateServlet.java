package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


@WebServlet("/manager/deliverState")
public class ChangeDeliverStateServlet extends HttpServlet {

    @Autowired
    private SecondhandOrderService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        SecondhandOrder od = CommonUtil.json2pojo(req, SecondhandOrder.class);
        SERVICE.editOd(od);

    }
}
