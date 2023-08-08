package gg.nbp.web.shop.shoporder.controller.shoplist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;
import gg.nbp.web.shop.shoporder.util.Test;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/AddShopList")
public class AddShopListController {
	
	private ShoppingListService shopService;
	
	@Autowired
	private Gson gson;

	@Autowired
	public AddShopListController(ShoppingListService ss) {
		this.shopService = ss;
	}
	
//	@PostMapping
	@RequestMapping(value = "/AddShopList")
	public boolean addOne(@RequestBody TransOrderProduct transObj, @SessionAttribute Member member) {
		return shopService.addOneShoppingList(transObj, member.getMember_id());
	}
}
