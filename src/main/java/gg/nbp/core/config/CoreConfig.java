package gg.nbp.core.config;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gg.nbp.core.service.MailService;
import redis.clients.jedis.Jedis;

@Configuration
public class CoreConfig {
		
	@Bean 
	public MailService mailService() throws IllegalArgumentException, NamingException {
		return new MailService();
	}
	
	
}
