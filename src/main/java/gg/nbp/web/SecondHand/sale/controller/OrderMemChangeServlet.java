package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.impl.MemberServiceimpl;
import gg.nbp.web.Member.util.MemberCommonUitl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/member/orderMemChange")
public class OrderMemChangeServlet extends HttpServlet {

    @Autowired
    private MemberServiceimpl SERVICE;

    boolean state = true;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member newInfo = CommonUtil.json2pojo(req, Member.class);

        try {
            if (newInfo.getPhone().trim().isEmpty() || newInfo.getEmail().trim().isEmpty()){
                newInfo = new Member();
                newInfo.setMessage("無二手商品資訊");
                state = false;
            }

            Member mem = MemberCommonUitl.getMemberSession(req,"member");
            mem = SERVICE.selectMember(mem.getMember_id());

            mem.setPhone(newInfo.getPhone());
            mem.setEmail(newInfo.getEmail());

            SERVICE.edit(mem);

            newInfo.setSuccessful(true);
            CommonUtil.writepojo2Json(resp, newInfo);

        } catch (Exception e) {
            e.printStackTrace();
            newInfo = new Member();
            newInfo.setSuccessful(false);
            CommonUtil.writepojo2Json(resp, newInfo);
        }



    }
}
