package gg.nbp.web.shop.shoporder.dao.impl;

import org.springframework.stereotype.Repository;

import gg.nbp.web.shop.shoporder.dao.JedisOrderMasterDao;
import redis.clients.jedis.Jedis;

@Repository
public class JedisOrderMasterDaoImpl implements JedisOrderMasterDao{

	@Override
	public boolean saveOrderMasterResults(String key, String content) {
		Jedis jedis = getJedis();
		jedis.select(3);
		jedis.set(key, content);
		jedis.close();
		return true;
	}

	@Override
	public String getResults(String key) {
		Jedis jedis = getJedis();
		jedis.select(3);
		String results = jedis.get(key);
		jedis.close();
		return results;
	}
	
}
