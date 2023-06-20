package gg.nbp.web.Member.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.service.MailService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemerCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/memberForgetServlet")
public class MemberForgetServlet extends HttpServlet {
    private static  final long serialVersionUID = 5152388072271321070L;
    
    @Autowired
	private MemberService SERVICE ;
    @Autowired
    private MailService SENDMAIL;
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Member member = new Member();
        member.setAccount(request.getParameter("account"));
        member.setEmail(request.getParameter("email"));

        Member fMember = SERVICE.forgetPassword(member);
        if(fMember.isSuccessful()){
            String title = "NBP.gg 密碼重置信";
            String randomPassword = MemerCommonUitl.verificationCode();
            String messageContext = "您重製的密碼是：" + randomPassword + " ,請登入後重置密碼。";
            SENDMAIL.mailService(fMember, title, messageContext);
            fMember.setPassword(randomPassword);
            SERVICE.edit(fMember);

            MemerCommonUitl.gsonToJson(response, MemerCommonUitl.visitorData(fMember));
        } else {
        	MemerCommonUitl.gsonToJson(response, MemerCommonUitl.visitorData(fMember));
        }
    }
}
