package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shopproduct.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailServiceImpl implements EmailService {

//    @Qualifier("shopSendEmail")
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMessage(
            String to, String subject, String text) {

//        ...shopSendEmail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
//        ...
    }

    @Override
    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) {
        // ...

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        // ...
    }
}
