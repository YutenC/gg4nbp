package gg.nbp.web.Member.controller;


import java.io.IOException;
import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.service.MailService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.DigestUtils;

@WebServlet("/memberForgetServlet")
public class MemberForgetServlet extends HttpServlet {
    @Serial
    private static  final long serialVersionUID = 5152388072271321070L;
    @Autowired
	private MemberService SERVICE ;
    @Autowired
    private MailService SENDMAIL;
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = new Member();
        member.setAccount(request.getParameter("account"));
        member.setEmail(request.getParameter("email"));

        Member fMember = SERVICE.forgetPassword(member);
        if(fMember.isSuccessful()){
            String title = "NBP.gg 密碼重置信";
            String randomPassword = MemberCommonUitl.verificationCode();
            String messageContext = "您重製的密碼是：" + randomPassword + " ,請登入後重置密碼。";
            SENDMAIL.mailService(fMember, title, messageContext);

            /*  將重製的密碼用MD5的方式轉換成雜湊值  */
            String hashedPassword = DigestUtils.md5DigestAsHex(randomPassword.getBytes());
            fMember.setPassword(hashedPassword);
            SERVICE.edit(fMember);

            MemberCommonUitl.gsonToJson(response, MemberCommonUitl.visitorData(fMember));
        } else {
        	MemberCommonUitl.gsonToJson(response, MemberCommonUitl.visitorData(fMember));
        }
    }
}
