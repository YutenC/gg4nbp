package gg.nbp.core.config;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gg.nbp.core.service.MailService;
import jakarta.annotation.PreDestroy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class CoreConfig {
	static private Jedis jedis ;
	@Bean 
	public MailService mailService() throws IllegalArgumentException, NamingException {
		return new MailService();
	}
	
	@SuppressWarnings("resource")
	@Bean
	public Jedis jedis() {
		jedis = new JedisPool("localhost", 6379).getResource();
		return jedis;
	}
	
	@PreDestroy
	static public void jedisClose() {
		jedis.close();
	}
	
}