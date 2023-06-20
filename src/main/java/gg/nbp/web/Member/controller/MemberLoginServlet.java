package gg.nbp.web.Member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemerCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/memberLoginServlet")
public class MemberLoginServlet extends HttpServlet {
	
    private static  final long serialVersionUID = 1L;
    @Autowired
   	private MemberService SERVICE ;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");      // 設定輸入文字的編碼
        Member member = new Member();
        member.setAccount(request.getParameter("account").trim());
        member.setPassword(request.getParameter("password").trim());
        if ((member.getAccount().equals("")) || (member.getPassword().equals(""))) {
            member = new Member();
            member.setMessage("請填寫帳號密碼");
            member.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response,member);
            return;
        }
        member = SERVICE.login(member);
        System.out.println("訊息：會員 " + member.getNick() + " " + member.getMessage());
        //  是否登入成功的訊息

        if (member.isSuccessful()) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            session.setAttribute("isLogin", true);
            session.setAttribute("member", member);
        }

        Member visitor = MemerCommonUitl.visitorData(member);
        MemerCommonUitl.gsonToJson(response, visitor);
    }
}
