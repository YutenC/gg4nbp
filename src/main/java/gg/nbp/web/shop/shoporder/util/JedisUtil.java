package gg.nbp.web.shop.shoporder.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	
	private static JedisPool pool = null;
	
	private JedisUtil() {}
	
	public static JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisUtil.class) {
				if (pool == null) {
					JedisPoolConfig jedisPlCfig = new JedisPoolConfig();
					jedisPlCfig.setMaxTotal(8);
					jedisPlCfig.setMaxIdle(8);
					jedisPlCfig.setMaxWaitMillis(10000);
					pool = new JedisPool(jedisPlCfig, "localhost", 6379);
					
				}
			}
		}
		return pool;
	}
	
	public static void shutdownJedisPool() {
		if (pool != null) {
			pool.destroy();
		}
	}
	
}
