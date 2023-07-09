package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.impl.MemberServiceimpl;
import gg.nbp.web.Member.util.MemberCommonUitl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/member/orderMemChange")
public class OrderMemChangeServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1870869297774410897L;
	@Autowired
    private MemberServiceimpl SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member newInfo = CommonUtil.json2pojo(req, Member.class);

        Member mem = MemberCommonUitl.getMemberSession(req,"member");
        mem = SERVICE.selectMember(mem.getMember_id());

        mem.setPhone(newInfo.getPhone());
        mem.setEmail(newInfo.getEmail());

        SERVICE.edit(mem);

    }
}
