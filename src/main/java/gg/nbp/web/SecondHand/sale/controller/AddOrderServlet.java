package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
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

@WebServlet("/secondhand/shp_buy")
public class AddOrderServlet extends HttpServlet {

    @Autowired
    private SecondhandOrderServiceImpl SERVICE;

    @Autowired
    private SecondhandProductServiceImpl PROSERVICE;

    int shpPrice = 0;
    long deliFee = 0;
    long totalPrice = 0;
    boolean state = true;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SecondhandOrder od = CommonUtil.json2pojo(req, SecondhandOrder.class);

        try {

            Member member = MemberCommonUitl.getMemberSession(req,"member");
            int memberId = member.getMember_id();

            if (od.getDeliverName().trim().isEmpty() || od.getDeliverLocation().trim().isEmpty() || od.getPayState().describeConstable().isEmpty()){
                od = new SecondhandOrder();
                od.setMessage("無二手商品資訊");
                state = false;
            }


            od.setMemberId(memberId);
            od.setProductId(od.getProductId());
            od.setReceive(od.getReceive());
            od.setDeliverName(od.getDeliverName());
            od.setDeliverLocation(od.getDeliverLocation());
            od.setPayBank(od.getPayBank());
            od.setPayNumber(od.getPayNumber());


            // 計算總金額
            SecondhandProduct shp = new SecondhandProduct();

            shp = PROSERVICE.selectOne(od.getProductId());
            shpPrice = shp.getPrice();

            if ((od.getReceive()) == 1){
                deliFee = 100;
            } else if ((od.getReceive()) == 2){
                deliFee = 60;
            }

            totalPrice = (long) shpPrice + deliFee;

            od.setDeliverFee(deliFee);
            od.setTotalPrice(totalPrice);

            System.out.println("delivername=" + od.getDeliverName());
            SERVICE.addOd(od);

            // 訂單成立修改為下架狀態
            shp.setIsLaunch("0");
            PROSERVICE.editshp(shp);

            od.setSuccessful(true);
            CommonUtil.writepojo2Json(resp, od);

        } catch (Exception e) {
            e.printStackTrace();
            od = new SecondhandOrder();
            od.setSuccessful(false);
            CommonUtil.writepojo2Json(resp, od);
        }


    }
}

//變成訂單的同時更改product狀態為下架