package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.Member.service.impl.NoticeServiceImpl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.service.SecondhandOrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


@WebServlet("/manager/deliverState")
public class ChangeDeliverStateServlet extends HttpServlet {

    @Autowired
    private SecondhandOrderService SERVICE;

    @Autowired
    private NoticeServiceImpl NOTICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        SecondhandOrder od = CommonUtil.json2pojo(req, SecondhandOrder.class);

        // 取得管理員登入資料
        HttpSession session= req.getSession(false);
        Manager loggedManager= (Manager) session.getAttribute("manager");
        Integer managerId = loggedManager.getManager_id();

        od.setManagerId(managerId);
        od.setDeliverState(od.getDeliverState());

        SERVICE.editOd(od);

        // 發送出貨通知

        if(od.getDeliverState() == 1) {
            Notice notice = new Notice();
            notice.setMember_id(od.getMemberId());
            notice.setNotice_value("二手訂單（訂單編號：" + od.getOrderId() + "）已出貨");
            NOTICE.addNotice(notice);
        }

    }
}
