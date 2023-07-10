package gg.nbp.web.Act.controller;


import gg.nbp.web.Act.model.Message;
import gg.nbp.web.Act.redis.RedisChatStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatController {

    private RedisChatStorage chatStorage;

    @Autowired
    public ChatController(RedisChatStorage chatStorage) {
        this.chatStorage = chatStorage;
    }

    @GetMapping("/getChatMessages")
    public List<Message> getChatMessages() {
        return chatStorage.getChatMessages();
    }

}
