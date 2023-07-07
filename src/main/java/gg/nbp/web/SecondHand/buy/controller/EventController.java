package gg.nbp.web.SecondHand.buy.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.nbp.core.pojo.Core;
import gg.nbp.core.pojo.OneString;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.SecondHand.buy.service.SecondHandBuyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@CrossOrigin
public class EventController {
	@Autowired
	private SecondHandBuyService service;

	@GetMapping("/deleteBuyEvent/{id}")
	public Object delete(@PathVariable("id") int EventId, HttpServletRequest req) {
		try {
			Member member = (Member) req.getSession().getAttribute("member");
			service.delete(member.getMember_id(), EventId);
			return service.searchAll(member);
		} catch (SQLException e) {
			e.printStackTrace();
			return new OneString("刪除失敗");
		}
	}

	@GetMapping("/isLogin")
	public Boolean isLogin(HttpServletRequest req) {
		final HttpSession session = req.getSession();
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		Core re = new Core();
		re.setSuccessful(isLogin == null ? false : true);
		return re.isSuccessful();

	}
	
	

}
