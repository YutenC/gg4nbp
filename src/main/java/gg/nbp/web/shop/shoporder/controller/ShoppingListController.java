package gg.nbp.web.shop.shoporder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ShoppingList")
public class ShoppingListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ShoppingListService shoppingListService;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession httpSession = req.getSession();
		Member odmember = new Member();
		odmember.setMember_id(1);

		Gson gson = new Gson();
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
		httpSession.setAttribute("member", odmember);
		Member member = (Member)httpSession.getAttribute("member");
		Integer memberId = null;
		
		if (member != null) {
			memberId = member.getMember_id();
		} else {
			res.sendRedirect("/Five_NBP.gg");
			return;
		}
		
		if (req.getParameter("getAll") != null) {
			List<TransOrderProduct> trspList = shoppingListService.getAllShoppingList(memberId);
			pw.println(gson.toJson(trspList));
			return;
		}
		
		if (req.getParameter("removeItem") != null) {
			PKShoppingList pksh = new PKShoppingList(memberId, Integer.valueOf(req.getParameter("removeItem")));
			ShoppingList splist = new ShoppingList();
			splist.setPkShoppingList(pksh);
			List<ShoppingList> spLists = new ArrayList<>();
			spLists.add(splist);
			boolean result = shoppingListService.removeItem(spLists);
			pw.println(result);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		
		HttpSession httpSession = req.getSession();
		
		Member mber = new Member();
		mber.setMember_id(1);
		httpSession.setAttribute("meber", mber);
		
		if (httpSession.getAttribute("member") == null) {
			res.sendRedirect("/Five_NBP.gg");
			return;
		}
		
		Member member = (Member)httpSession.getAttribute("member");
		Integer memberId = member.getMember_id();
		
		String demand = req.getParameter("demand");
		
		if ("addOneShoppingList".equals(demand)) {
			TransOrderProduct trpd = gson.fromJson(req.getParameter("transObj"), TransOrderProduct.class);
			boolean result = shoppingListService.addOneShoppingList(trpd, memberId);
			pw.print(gson.toJson(result));
			return;
		}
		
		// OrderMaster include ShoppingList(移除已結帳商品)
		if ("checkOut".equals(demand)) {
		List<TransOrderProduct> purchaseProducts = (List<TransOrderProduct>)req.getAttribute("purchaseProducts");
		if (purchaseProducts != null) {
			
			List<ShoppingList> spLists = new ArrayList<>();
			
			for (TransOrderProduct trObj : purchaseProducts) {
				PKShoppingList pksplist = new PKShoppingList();
				pksplist.setMemmberId(memberId);
				pksplist.setProductId(trObj.getProductId());
				
				ShoppingList splist = new ShoppingList();
				splist.setPkShoppingList(pksplist);
				
				spLists.add(splist);
			}
			
			boolean rmSplist = shoppingListService.removeItem(spLists);
			req.setAttribute("rmSplist", rmSplist);
			req.removeAttribute("trObjList");
			return;
		} else {
			return;
		}
	}
  }
}
