package gg.nbp.web.Member.controller;

import java.io.IOException;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/memberLogoutServlet")
public class MemberLogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request,"member");
        System.out.println("會員：" + member.getNick() + " 成功登出");
        request.getSession().setAttribute("isLogin", false);

        request.getSession().removeAttribute("isLogin");
        request.getSession().removeAttribute("member");
        MemberCommonUitl.gsonToJson(response,member);
    }
}
