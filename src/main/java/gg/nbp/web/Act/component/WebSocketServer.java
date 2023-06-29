package gg.nbp.web.Act.component;

import com.alibaba.fastjson.JSON;

import gg.nbp.web.Act.SpringApplication.SpringApplicationContextHolder;
import gg.nbp.web.Act.model.Message;
import gg.nbp.web.Act.model.UserList;
import gg.nbp.web.Act.redis.RedisChatStorage;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服務
 */
@CrossOrigin
@Component
@ServerEndpoint("/socket/{username}")
public class WebSocketServer {
    /**
     * 存儲物件的 Map
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /***
     * WebSocket 建立連接事件
     * 1. 將登入的使用者存到 sessionMap 中
     * 2. 傳送給所有人目前登入人員資訊
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if(chatStorage == null) {
            chatStorage = SpringApplicationContextHolder.getContext().getBean(RedisChatStorage.class);
        }
        // 搜尋名稱是否存在
        boolean isExist = sessionMap.containsKey(username);
        if (!isExist) {
            System.out.println(username + "加入了聊天室");
            sessionMap.put(username, session);
            sendAllMessage(setUserList());
            showUserList();
        }
    }

    /**
     * WebSocket 關閉連線事件
     * 1. 從 sessionMap 中移除登出的使用者
     * 2. 傳送給所有人目前登入人員資訊
     */
    @OnClose
    public void onClose(@PathParam("username") String username) {
        if (username != null) {
            System.out.println(username + "退出了聊天室");
            sessionMap.remove(username);
            sendAllMessage(setUserList());
            showUserList();
        }
    }

    /**
     * WebSocket 接收訊息事件
     * 接收並處理客戶端發來的資料
     * @param message 訊息
     */
    @Autowired
    private RedisChatStorage chatStorage;

    @OnMessage
    public void onMessage(String message) {
        Message msg = JSON.parseObject(message, Message.class);
        chatStorage.saveChatMessage(msg.getUsername(), msg.getMessage());
        sendAllMessage(JSON.toJSONString(msg));
    }


    /**
     * WebSocket 錯誤事件
     * @param session 使用者 Session
     * @param error 錯誤資訊
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("錯誤");
        error.printStackTrace();
    }

    /**
     * 顯示線上使用者
     */
    private void showUserList() {
        System.out.println("------------------------------------------");
        System.out.println("當前人數");
        System.out.println("------------------------------------------");
        for (String username : sessionMap.keySet()) {
            System.out.println(username);
        }
        System.out.println("------------------------------------------");
        System.out.println();
    }

    /**
     * 設置接收訊息的使用者列表
     * @return 使用者列表
     */
    private String setUserList(){
        ArrayList<String> list = new ArrayList<>(sessionMap.keySet());
        UserList userList = new UserList();
        userList.setUserlist(list);
        return JSON.toJSONString(userList);
    }

    /**
     * 將訊息發送給所有使用者
     * @param message 訊息
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
