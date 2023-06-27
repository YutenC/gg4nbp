package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.service.MailService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.JedisCommonUtil;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/memberSendVertifyServlet")
public class MemberSendVertifyServlet extends HttpServlet {

        @Serial
        private static final long serialVersionUID = 6278567220887318688L;
        @Autowired
        private MailService SENDMAIL;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Member member = new Member();
        member.setEmail(request.getParameter("email"));
        String verificationCode = MemberCommonUitl.verificationCode();
        String title = "NBP.gg 會員驗證信";
        String messageText = "您的驗證密碼為：" + verificationCode + " 請於30秒內完成會員驗證";
        SENDMAIL.mailService(member, title, messageText);

        JedisCommonUtil.saveCodes(member, verificationCode);        // 將驗證碼存入Redis中，驗證碼存活30s，需要用信箱比對

        member.setMessage("驗證碼已寄出");
        MemberCommonUitl.gsonToJson(response,member);
    }
}
