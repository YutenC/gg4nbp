package gg.nbp.web.Act.redis;

import com.alibaba.fastjson.JSON;

import gg.nbp.web.Act.model.Message;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedisChatStorage {

    public void saveChatMessage(String username, String message) {
        try (Jedis jedis = new Jedis("localhost")) {
            Message chatMessage = new Message();
            chatMessage.setUsername(username);
            chatMessage.setMessage(message);
            jedis.rpush("chat_messages", JSON.toJSONString(chatMessage));
        }
    }
    public List<String> getHistoryMessages() {
        // 建立Redis客戶端
        try (Jedis jedis = new Jedis("localhost")) {
            // 從Redis獲取歷史消息
            return jedis.lrange("chat_messages", 0 , -1);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Message> getChatMessages() {
        try (Jedis jedis = new Jedis("localhost")) {
            List<String> messages = jedis.lrange("chat_messages", 0, -1);
            return messages.stream().map(msg -> JSON.parseObject(msg, Message.class)).collect(Collectors.toList());
        }
    }
}
