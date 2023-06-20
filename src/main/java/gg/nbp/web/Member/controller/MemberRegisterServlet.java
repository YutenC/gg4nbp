package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.sql.Date;

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


@WebServlet("/memberRegisterServlet")
public class MemberRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Autowired
   	private MemberService SERVICE ;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");      // 設定請求的編碼為UTF-8
        Member register = new Member();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String nick = request.getParameter("nick");
        String idNumber = request.getParameter("id_number");
        String birthday = request.getParameter("birth");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (!account.equals("")) {
            register.setAccount(account);
        } else {
            register = new Member();
            register.setMessage("帳號格式錯誤");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (password.equals(confirmPassword)) {
            register.setPassword(password);
        } else {
            register = new Member();
            register.setMessage("密碼與確認密碼不一致");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!nick.equals("")) {
            register.setNick(nick);
        } else {
            register = new Member();
            register.setMessage("請輸入暱稱");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!idNumber.equals("")) {
            register.setId_number(idNumber);
        } else {
            register = new Member();
            register.setMessage("請輸入身分證字號");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!birthday.equals("")) {
            Date birth = Date.valueOf(birthday);
            register.setBirth(birth);
        } else {
            register = new Member();
            register.setMessage("請輸入生日");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!address.equals("")) {
            register.setAddress(address);
        } else {
            register = new Member();
            register.setMessage("請輸入聯絡地址");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!phone.equals("")) {
            register.setPhone(phone);
        } else {
            register = new Member();
            register.setMessage("請輸入聯絡電話");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
            return;
        }
        if (!email.equals("")) {
            register.setEmail(email);
        } else {
            register = new Member();
            register.setMessage("請輸入電子郵件");
            register.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, register);
        }
        register.setBonus(0.0);
        register.setMember_ver_state(0);
        register.setSuspend_deadline(null);
        register.setHeadshot(null);
        register.setVer_deadline(null);
        register.setViolation(0);

        Member member = MemerCommonUitl.visitorData(SERVICE.register(register));
        System.out.println("訊息：會員 " + member.getNick() + "申請註冊");

        if (member.isSuccessful()) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            session.setAttribute("register", register);
        }
        MemerCommonUitl.gsonToJson(response, member);
    }
}
