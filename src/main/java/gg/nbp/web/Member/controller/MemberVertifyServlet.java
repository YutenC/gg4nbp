package gg.nbp.web.Member.controller;

import java.io.IOException;

import gg.nbp.web.Member.entity.Notice;
import gg.nbp.web.Member.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.JedisCommonUtil;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/memberVertifyServlet")
public class MemberVertifyServlet extends HttpServlet {
	
	@Autowired
	private MemberService SERVICE ;
    @Autowired
    private NoticeService NOTICE;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member register = new Member();
        String email = request.getParameter("email");
        String verfiyCode = request.getParameter("verification_code");     // 填入的驗證碼

        register.setEmail(email);
        Member member = JedisCommonUtil.getCodes(register, verfiyCode);
        Boolean isPass =  member.isSuccessful();

        if(isPass){
            Member passRegister = MemberCommonUitl.getMemberSession(request,"register");   // 從session中取得註冊會員資訊
            Member passMember = MemberCommonUitl. getMemberSession(request,"member");
            if(passRegister != null){
                System.out.println(1);
                passRegister.setMember_ver_state(1);
                passRegister = SERVICE.edit(passRegister);       // 修改會員驗證狀態
                request.getSession().removeAttribute("register");   // 移除seesion中會員註冊資訊
                passRegister.setMessage("驗證成功");

                Notice notice = new Notice();

                notice.setMember_id(passRegister.getMember_id());
                notice.setIs_read(1);
                notice.setNotice_value("歡迎會員 " + passRegister.getNick() + " 加入NBPgg ");
                NOTICE.addNotice(notice);

                MemberCommonUitl.gsonToJson(response, passRegister);

            } else if(passMember != null){
                System.out.println(2);
                passMember.setMember_ver_state(1);
                SERVICE.edit(passMember);
                passMember.setMessage("驗證成功");

                Notice notice = new Notice();
                notice.setMember_id(passMember.getMember_id());
                notice.setIs_read(1);
                notice.setNotice_value("歡迎會員 " + passMember.getNick() + " 加入NBPgg ");
                NOTICE.addNotice(notice);

                MemberCommonUitl.gsonToJson(response, MemberCommonUitl.visitorData(passMember));
            }
        } else {
        	MemberCommonUitl.gsonToJson(response, member);
        }
    }
}
