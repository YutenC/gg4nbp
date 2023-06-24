package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.io.Serial;

import gg.nbp.web.Member.entity.Login_record;
import gg.nbp.web.Member.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.DigestUtils;

@WebServlet("/memberLoginServlet")
public class MemberLoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = -5239242805789930921L;
    @Autowired
   	private MemberService SERVICE ;
    @Autowired
    private LoginRecordService LOGIN_SERVICE;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = new Member();
        Login_record loginRecord = new Login_record();
        member.setAccount(request.getParameter("account").trim());
        String password = request.getParameter("password").trim();
        String loginDevice = request.getParameter("login_device");
        String loginCity = request.getParameter("login_city");
        String ip = request.getParameter("host_name");

        if ((member.getAccount().equals("")) || (password.equals(""))) {
            member = new Member();
            member.setMessage("請填寫帳號密碼");
            member.setSuccessful(false);
            MemberCommonUitl.gsonToJson(response,member);
            return;
        }

        /*  將登入的密碼用MD5的方式轉換成雜湊值  */
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        member.setPassword(hashedPassword);
        /*  然後將密碼帶入比對是否登入成功  */
        member = SERVICE.login(member);
        //  是否登入成功的訊息

        if (member.isSuccessful()) {

            loginRecord.setMember_id(member.getMember_id());
            loginRecord.setLogin_device(loginDevice);
            loginRecord.setLogin_city(loginCity);
            loginRecord.setHost_name(ip);
            LOGIN_SERVICE.record(loginRecord);

            System.out.println("訊息：會員 " + member.getNick() + " " + member.getMessage());
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            session.setAttribute("isLogin", true);
            session.setAttribute("member", member);
        }

        Member visitor = MemberCommonUitl.visitorData(member);
        MemberCommonUitl.gsonToJson(response, visitor);
    }
}
