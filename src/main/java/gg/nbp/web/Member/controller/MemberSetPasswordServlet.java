package gg.nbp.web.Member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.DigestUtils;

@WebServlet("/memberSetPasswordServlet")
public class MemberSetPasswordServlet extends HttpServlet {
    private static  final long serialVersionUID = 1L;
    @Autowired
	private MemberService SERVICE ;
    
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

        String originPassword = member.getPassword();
        /*  將重設的密碼用MD5轉換成雜湊值，後續比對是否符合原密碼  */
        String password = DigestUtils.md5DigestAsHex(request.getParameter("originPassword").getBytes());
        String setPassword = request.getParameter("setPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if(!originPassword.equals(password)){
            Member visitor = new Member();
            visitor.setMessage("與原密碼不一致");
            visitor.setSuccessful(false);
            MemberCommonUitl.gsonToJson(response, visitor);
            return;
        }
        if(!setPassword.equals(confirmPassword)){
            Member visitor = new Member();
            visitor.setMessage("修改密碼不一致");
            visitor.setSuccessful(false);
            MemberCommonUitl.gsonToJson(response, visitor);
            return;
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(setPassword.getBytes());
        member.setPassword(hashedPassword);
        member = SERVICE.edit(member);
        System.out.println("訊息：會員 " + member.getNick() + " 重設密碼");

        Member visitor = MemberCommonUitl.visitorData(member);
        MemberCommonUitl.gsonToJson(response,visitor);
    }
}
