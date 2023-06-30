package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.JsonObject;
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
        member.setAccount(request.getParameter("account"));
        String password = request.getParameter("password");
        String loginDevice = request.getParameter("login_device");
        String loginCity = request.getParameter("login_city");
        String ip = request.getParameter("host_name");
        System.out.println(loginCity);

        if ((member.getAccount().equals("")) || (password.equals(""))) {
            member = new Member();
            member.setMessage("請填寫帳號密碼");
            member.setSuccessful(false);
            MemberCommonUitl.gsonToJson(response,member);
            return;
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        member.setPassword(hashedPassword);
        member = SERVICE.login(member);

        if(!member.isSuccessful()){
            MemberCommonUitl.gsonToJson(response,member);
            return;
        }

        
        //停權檢查
        
        boolean isSuspending= false;
        
        if (member.getMember_ver_state() == 2) {
        	LocalDate currentDate = LocalDate.now();
            LocalDate ban_deadline = member.getSuspend_deadline().toLocalDate();
        	if (currentDate.isAfter(ban_deadline)) {
        		member.setSuspend_deadline(null);
        		member.setMember_ver_state(1);
        		SERVICE.edit(member);
            }else {
            	isSuspending= true;
            }
        }else if (member.getMember_ver_state() == 3) {
        	isSuspending= true;
        }
        
        if (isSuspending) {
        	member.setSuccessful(false);
        	member.setMessage("此帳號已停權，請洽客服人員");
        	MemberCommonUitl.gsonToJson(response,member);
            return;
        }
        
        
        
        if (member.isSuccessful()) {
            if(loginCity == null){
                loginCity = "使用者未開啟定位";
            }
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

        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("account", visitor.getAccount());
        responseJson.addProperty("member_id", visitor.getMember_id());
        responseJson.addProperty("successful", visitor.isSuccessful());
        responseJson.addProperty("redirectUrl", (String) request.getSession().getAttribute("memberLocation")); // 設置重導的網址

        MemberCommonUitl.gsonToJson(response, responseJson);
    }
}
