package gg.nbp.web.Member.controller;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/member/memberGetNoticeServlet")
public class MemberGetNoticeServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 5531921489630390347L;
    @Autowired
    private NoticeService NOTICE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Member member = MemberCommonUitl.getMemberSession(request,"member");

    Notice notice = new Notice();
    notice.setMember_id(member.getMember_id());

    MemberCommonUitl.gsonToJson(response, NOTICE.findAll(notice));
    }
}
