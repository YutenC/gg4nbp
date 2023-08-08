package gg.nbp.web.shop.shoporder.controller.shoplist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;

@RestController
public class RemoveShopListController {
	
	@Autowired
	private ShoppingListService shoppingListService;
	
	@GetMapping("/RemoveShopList/{productId}")
	public boolean removeShopList(@SessionAttribute Member member, @PathVariable Integer productId) {
		PKShoppingList pksh = new PKShoppingList(member.getMember_id(), productId);
		ShoppingList splt = new ShoppingList();
		splt.setPkShoppingList(pksh);
		List<ShoppingList> spLists = new ArrayList<>();
		spLists.add(splt);
		return shoppingListService.removeItem(spLists);
	}
}
