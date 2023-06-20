package gg.nbp.web.Member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.JedisCommonUtil;
import gg.nbp.web.Member.util.MemerCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/memberVertifyServlet")
public class MemberVertifyServlet extends HttpServlet {
	
	@Autowired
	private MemberService SERVICE ;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Member register = new Member();
        String email = request.getParameter("email");
        String verfiyCode = request.getParameter("verification_code");     // 填入的驗證碼

        register.setEmail(email);
        Member member = JedisCommonUtil.getCodes(register, verfiyCode);
        Boolean isPass =  member.isSuccessful();
        if(isPass){
            Member passRegister = MemerCommonUitl.getMemberSession(request,"register");   // 從session中取得註冊會員資訊
            if(passRegister != null){
                passRegister.setMember_ver_state(1);
                SERVICE.edit(passRegister);       // 修改會員驗證狀態
                request.getSession().removeAttribute("register");   // 移除seesion中會員註冊資訊
                passRegister.setMessage("驗證成功");
                MemerCommonUitl.gsonToJson(response, passRegister);
            }
            Member passMember =MemerCommonUitl. getMemberSession(request,"member");
            if(passMember != null){
                passMember.setMember_ver_state(1);
                SERVICE.edit(passMember);
                passMember.setMessage("驗證成功");
                MemerCommonUitl.gsonToJson(response, MemerCommonUitl.visitorData(passMember));
            }
        } else {
        	MemerCommonUitl.gsonToJson(response, member);
        }
    }
}
