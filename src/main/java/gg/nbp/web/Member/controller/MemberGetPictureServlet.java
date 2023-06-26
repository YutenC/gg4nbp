package gg.nbp.web.Member.controller;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serial;

@WebServlet({"/memberGetPictureServlet","/member/memberGetPictureServlet"})
public class MemberGetPictureServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -3659793488389285126L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request,"member");
        if(member == null){
            Member visitor = new Member();
            visitor.setMessage("無會員資訊");
            visitor.setSuccessful(false);
            MemberCommonUitl.gsonToJson(response, visitor);
            return;
        }
        Member headshot = new Member();
        headshot.setHeadshot(member.getHeadshot());
        headshot.setSuccessful(true);
        MemberCommonUitl.gsonToJson(response,headshot);
    }
}
