package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


@WebServlet("/manager/payState")
public class ChangePayStateServlet extends HttpServlet {

    @Autowired
    private SecondhandOrderService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        SecondhandOrder od = CommonUtil.json2pojo(req, SecondhandOrder.class);

        // 取得管理員登入資料
        HttpSession session= req.getSession(false);
        Manager loggedManager= (Manager) session.getAttribute("manager");
        Integer managerId = loggedManager.getManager_id();

        od.setManagerId(managerId);
        od.setPayState(od.getPayState());

        SERVICE.editOd(od);

    }
}
