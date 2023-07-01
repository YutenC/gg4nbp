package gg.nbp.web.Act.component;

import com.alibaba.fastjson.JSON;
import gg.nbp.web.Act.SpringApplication.SpringApplicationContextHolder;
import gg.nbp.web.Act.model.Message;
import gg.nbp.web.Act.redis.RedisChatStorage;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/socket/{chatId}/{username}")
public class WebSocketServer {
    public static final Map<String, Map<String, Session>> sessionMap = new ConcurrentHashMap<>();
    private RedisChatStorage chatStorage;

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") String chatId, @PathParam("username") String username) {
        if(chatStorage == null) {
            chatStorage = SpringApplicationContextHolder.getContext().getBean(RedisChatStorage.class);
        }

        sessionMap.computeIfAbsent(chatId, k -> new ConcurrentHashMap<>()).put(username, session);
        // Rest of the code...
    }

    @OnClose
    public void onClose(@PathParam("chatId") String chatId, @PathParam("username") String username) {
        Map<String, Session> chatRoom = sessionMap.get(chatId);
        if (chatRoom != null) {
            chatRoom.remove(username);
            // Rest of the code...
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("chatId") String chatId) {
        Message msg = JSON.parseObject(message, Message.class);
        chatStorage.saveChatMessage(chatId, msg.getUsername(), msg.getMessage());
        sendAllMessage(chatId, JSON.toJSONString(msg));
    }

    private void sendAllMessage(String chatId, String message) {
        try {
            Map<String, Session> chatRoom = sessionMap.get(chatId);
            if (chatRoom != null) {
                for (Session session : chatRoom.values()) {
                    session.getBasicRemote().sendText(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Rest of the code...
}
