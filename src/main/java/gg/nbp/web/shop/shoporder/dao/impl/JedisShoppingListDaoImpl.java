package gg.nbp.web.shop.shoporder.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gg.nbp.web.shop.shoporder.dao.JedisShoppingListDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Repository
public class JedisShoppingListDaoImpl implements JedisShoppingListDao{
	
	private final long EXPIRELENGTH = 1 * 60 * 60 * 24 * 30;
	
	@Override
	public boolean insert(ShoppingList shoppingList) {
		Jedis jedis = getJedis();
		jedis.select(3);
		try {
			Transaction jediTx = jedis.multi();
			String key = "member:" + shoppingList.getPkShoppingList().getMemmberId();
			String productId = "product:" + shoppingList.getPkShoppingList().getProductId();
			String buyAmount = shoppingList.getQuantity().toString();
			Map<String, String> detail = new HashMap<>();
			detail.put(productId, buyAmount);
			jediTx.hmset(key, detail);
			jediTx.exec();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean delete(ShoppingList shoppingList) {
		Jedis jedis = getJedis();
		jedis.select(3);
		try {
			Transaction jediTx = jedis.multi();
			String key = "member:" + shoppingList.getPkShoppingList().getMemmberId();
			String field = "product:" + shoppingList.getPkShoppingList().getProductId();
			jediTx.hdel(key, field);
			jediTx.exec();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}
		
	}

	@Override
	public boolean update(ShoppingList shoppingList) {
		Jedis jedis = getJedis();
		jedis.select(3);
		try {
		Transaction jediTx = jedis.multi();
		String key = "member:" + shoppingList.getPkShoppingList().getMemmberId();
		String productId = "product:" + shoppingList.getPkShoppingList().getProductId();
		Integer buyAmount = shoppingList.getQuantity();
		jediTx.hincrBy(key, productId, buyAmount);
		jediTx.exec();
		return true;
		} catch (Exception e) {
			return false;
		} finally {
			jedis.close();
		}
	}

	@Override
	public List<ShoppingList> selectByMemberId(Integer memberId) {
		Jedis jedis = getJedis();
		jedis.select(3);
		try {
			List<ShoppingList> result = new ArrayList<>();
			String key = "member:" + memberId;
			Map<String, String> detail = jedis.hgetAll(key);
			for (String product : detail.keySet()) {
				PKShoppingList pkspList = new PKShoppingList();
				pkspList.setMemmberId(memberId);
				pkspList.setProductId(Integer.valueOf(product.split(":")[1]));
				ShoppingList spList = new ShoppingList();
				spList.setPkShoppingList(pkspList);
				spList.setQuantity(Integer.valueOf(detail.get(product)));
				result.add(spList);
			}
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean renewExpireDate(Integer memberId) {
		Jedis jedis = getJedis();
		jedis.select(3);
		Transaction jediTx = jedis.multi();
		
		List<ShoppingList> splists = selectByMemberId(memberId);
		try {
			for (ShoppingList sp : splists) {
				String memberKey = "member:" + sp.getPkShoppingList().getMemmberId();
				String productKey = "product:" + sp.getPkShoppingList().getProductId();
				String fullKey = memberKey + productKey;
				
				jediTx.expire(fullKey, EXPIRELENGTH);
			}
			jediTx.exec();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}
		
	}
	
}
