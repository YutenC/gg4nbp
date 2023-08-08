package gg.nbp.web.shop.shoporder.controller.shoplist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

@RestController
@RequestMapping("/ShopListInfo")
public class ShopListInfoController {

	@Autowired
	private ShoppingListService shoppingListService;
	
	@GetMapping("/all")
	public List<TransOrderProduct> getAll(@SessionAttribute Member member) {
		return shoppingListService.getAllShoppingList(member.getMember_id());
	}
}
