package gg.nbp.web.shop.shoporder.dao;

import java.util.List;

import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shopproduct.util.JedisUtil;
import redis.clients.jedis.Jedis;

public interface JedisShoppingListDao {
	
	boolean insert(ShoppingList shoppingList);
	
	boolean delete(ShoppingList shoppingList);
	
	boolean update(ShoppingList shoppingList);
	
	List<ShoppingList> selectByMemberId(Integer memberId);
	
	boolean renewExpireDate(Integer memberId);
	
	default Jedis getJedis() {
		return JedisUtil.getJedisPool().getResource();
	}
}
