package gg.nbp.web.Act.redis;

import com.alibaba.fastjson.JSON;
import gg.nbp.web.Act.model.Message;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

public class RedisChatStorage {

    private String chatId;

    public void saveChatMessage(String chatId, String username, String message) {
        try (Jedis jedis = new Jedis("localhost")) {
            Message chatMessage = new Message();
            chatMessage.setUsername(username);
            chatMessage.setMessage(message);
            jedis.rpush("chat_messages:" + chatId, JSON.toJSONString(chatMessage));
        }
    }

    public List<Message> getChatMessages() {
        try (Jedis jedis = new Jedis("localhost")) {
            List<String> messages = jedis.lrange("chat_messages:" + chatId, 0, -1);
            return messages.stream().map(msg -> JSON.parseObject(msg, Message.class)).collect(Collectors.toList());
        }
    }
}
