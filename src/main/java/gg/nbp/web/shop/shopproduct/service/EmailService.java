package gg.nbp.web.shop.shopproduct.service;

public interface EmailService {

    void sendMessage( String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
}
