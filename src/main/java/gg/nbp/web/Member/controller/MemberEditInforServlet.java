package gg.nbp.web.Member.controller;

import static gg.nbp.web.Member.util.MemberCommonUitl.*;
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

@WebServlet("/member/memberEditInforServlet")
public class MemberEditInforServlet extends HttpServlet {
	
	@Autowired
	private MemberService service ;
	
	
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

        member.setAddress(request.getParameter("address"));
        member.setPhone(request.getParameter("phone"));
        member.setEmail(request.getParameter("email"));
        member = service.edit(member);
        System.out.println("訊息：會員 " + member.getNick() + " 修改資料");

        Member visitor = visitorData(member);
        gsonToJson(response,visitor);
    }
}
