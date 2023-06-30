
package gg.nbp.web.Act.service;

import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundFactory;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.lang.invoke.CallSite;
import java.util.concurrent.Callable;


@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(String email, String subject, String message) {

        BackgroundHandler backgroundHandler= BackgroundFactory.getBackgroundHandler("actBackground");

        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setTo(email);
                    helper.setSubject(subject);
                    helper.setText(message, true); // Set the second parameter to 'true' to indicate you are sending HTML
                    javaMailSender.send(mimeMessage);
                    return "sendOK";
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return "sendFailed";
                }


            }
        };

        backgroundHandler.addTask("sendEmail",callable);

        return true;

//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            helper.setTo(email);
//            helper.setSubject(subject);
//            helper.setText(message, true); // Set the second parameter to 'true' to indicate you are sending HTML
//            javaMailSender.send(mimeMessage);
//            return true;
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return false;
//        }
    }
}
