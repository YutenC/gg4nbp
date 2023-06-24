package gg.nbp.web.Member.controller;

import gg.nbp.web.Member.entity.Login_record;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.LoginRecordService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/member/memberGetLoginServlet")
public class MemberGetLoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -6489692828179071693L;
    @Autowired
    private LoginRecordService LOGIN_SERVICE;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request,"member");
        Login_record loginRecord = new Login_record();
        loginRecord.setMember_id(member.getMember_id());

        MemberCommonUitl.gsonToJson(response, LOGIN_SERVICE.findAll(loginRecord));
    }
}
