package gg.nbp.web.shop.shoporder.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;

public interface ShoppingListDao extends CoreDao<ShoppingList, Integer>{

	ShoppingList selectByCompositePk(PKShoppingList pkshlist);
	
	List<ShoppingList> selectByMemberId(Integer memberId);
}
