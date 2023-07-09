package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.service.impl.NoticeServiceImpl;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/removeOneNoticeServlet")
public class RemoveOneNoticeServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID= 964246739172088494L;
    @Autowired
    public NoticeServiceImpl NOTICE;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer notice_id = (Integer.parseInt(request.getParameter("notice_id")));
        MemberCommonUitl.gsonToJson(response,NOTICE.remove(notice_id));
    }
}
