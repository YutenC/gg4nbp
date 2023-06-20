package gg.nbp.web.Member.controller;


import java.io.IOException;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.util.MemerCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/memberGetInforServlet")
public class MemberGetInforServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Member member = MemerCommonUitl.getMemberSession(request,"member");
        if(member == null){
            Member visitor = new Member();
            visitor.setMessage("無會員資訊");
            visitor.setSuccessful(false);
            MemerCommonUitl.gsonToJson(response, visitor);
            return;
        }
        Member visitor = MemerCommonUitl.visitorData(member);
        // 後端先過濾輸出到前端的資料

        System.out.println("訊息：會員 " + visitor.getNick() + " 取得資訊");
        MemerCommonUitl.gsonToJson(response, visitor);
    }
}
