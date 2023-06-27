package gg.nbp.web.shop.shoporder.dao;

import gg.nbp.web.shop.shoporder.util.JedisUtil;
import redis.clients.jedis.Jedis;

public interface JedisOrderMasterDao {
	
	default Jedis getJedis() {
		return JedisUtil.getJedisPool().getResource();
	}
	
	boolean saveOrderMasterResults(String key, String content);
	
	String getResults(String key);
}
