package gg.nbp.web.shop.shopproduct.config;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.util.CreateProductDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ShopProductConfig {

    @Bean
    public CreateProductDB<Product, ProductImage> createProductDB() {
        return new CreateProductDB<>(Product.class, ProductImage.class);
    }


//    @Bean(name="shopSendEmail")
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("  ");
//        mailSender.setPassword("  ");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
}
