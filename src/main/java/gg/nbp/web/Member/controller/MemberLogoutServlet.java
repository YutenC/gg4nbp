package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.io.Serial;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/member/memberLogoutServlet","/memberLogoutServlet"})
public class MemberLogoutServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1342428795951762191L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request,"member");
        System.out.println("訊息：會員 " + member.getNick() + " 成功登出");

        request.getSession().invalidate();
//        request.getSession().removeAttribute("isLogin");
//        request.getSession().removeAttribute("member");
//        request.getSession().removeAttribute("successful");
//        request.getSession().removeAttribute("redirectUrl");
        MemberCommonUitl.gsonToJson(response,member);
    }
}
