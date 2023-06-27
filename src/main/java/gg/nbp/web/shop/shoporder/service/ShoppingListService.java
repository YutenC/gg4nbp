package gg.nbp.web.shop.shoporder.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

public interface ShoppingListService extends CoreService{
	
	List<TransOrderProduct> getAllShoppingList(Integer memberId);
	
	boolean addOneShoppingList(TransOrderProduct trpd, Integer memberId);
	
	boolean removeItem(List<ShoppingList> spLists);

}
