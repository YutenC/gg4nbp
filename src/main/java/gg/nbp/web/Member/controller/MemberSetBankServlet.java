package gg.nbp.web.Member.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.web.Member.entity.Bank;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.BankService;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/memberSetBankServlet")
public class MemberSetBankServlet extends HttpServlet {
    private static final long serialVersionUID = -2575311513405024631L;
    @Autowired
    private BankService BANK_SERVICE;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = MemberCommonUitl.getMemberSession(request,"member");

        // action: 1->查詢全部帳戶 2->新增帳戶 3->刪除帳戶
        int action = Integer.parseInt(request.getParameter("action"));
        Bank bank = new Bank();
        bank.setMember_id(member.getMember_id());
        bank.setBank_name(request.getParameter("bank_name"));
        bank.setBank_number(request.getParameter("bank_number"));
        System.out.println(bank.getMember_id());
        System.out.println(bank.getBank_name());
        System.out.println(bank.getBank_number());

        switch (action){
            case 1:
                List<Bank> sbank = BANK_SERVICE.findAll();
                MemberCommonUitl.gsonToJson(response, sbank);
                break;
            case 2:
                BANK_SERVICE.add(bank);
                MemberCommonUitl.gsonToJson(response, bank);
                break;
            case 3:
                BANK_SERVICE.remove(bank.getBank_id());
                MemberCommonUitl.gsonToJson(response, bank);
                break;
        }
    }
}
