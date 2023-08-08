package gg.nbp.web.shop.shoporder.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpSession;

@RestController
public class NotLoginController {
	
	@RequestMapping("/notLogin")
	public JsonObject notLogin(HttpSession session, @ModelAttribute("memberLocation") String refer) {
		JsonObject failLogin = new JsonObject();
		failLogin.addProperty("redirect", true);
		session.setAttribute("memberLocation", refer);
		return failLogin;
	}
}
