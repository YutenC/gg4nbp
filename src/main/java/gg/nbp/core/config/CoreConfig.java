package gg.nbp.core.config;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gg.nbp.core.service.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class CoreConfig {

	@Bean 
	public MailService mailService() throws IllegalArgumentException, NamingException {
		return new MailService();
	}
}