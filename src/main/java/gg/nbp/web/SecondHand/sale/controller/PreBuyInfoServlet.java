package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.impl.MemberServiceimpl;
import gg.nbp.web.Member.util.MemberCommonUitl;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/member/preBuy")
public class PreBuyInfoServlet extends HttpServlet {


    @Autowired
    private MemberServiceimpl MEMSERVICE;

    @Autowired
    private SecondhandProductServiceImpl PROSERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member member = MemberCommonUitl.getMemberSession(req,"member");
        member = MEMSERVICE.selectMember(member.getMember_id());

        SecondhandProduct shp = CommonUtil.json2pojo(req, SecondhandProduct.class);
        shp = PROSERVICE.selectOne(shp.getProductId());

        Object[] objects = {member, shp};

        CommonUtil.writepojo2Json(resp, objects);


    }


}
