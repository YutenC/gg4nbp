package gg.nbp.web.Act.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import static gg.nbp.web.Act.component.WebSocketServer.sessionMap;

@CrossOrigin
@RestController
@RequestMapping("/list")
public class UserListController {

    /**
     * 判斷使用者名稱是否存在於聊天室
     * @param username 使用者名稱
     * @return JSON 物件
     */
    @GetMapping("/{username}")
    public JSONObject getUsername(@PathVariable("username") String username) {
        JSONObject jsonObject = new JSONObject();
        boolean isEmpty = sessionMap.isEmpty();
        jsonObject.put("isEmpty", isEmpty);
        jsonObject.put("isExist", false);
        if (!isEmpty) {
            boolean isExist = sessionMap.containsKey(username);
            jsonObject.replace("isExist", isExist);
        }
        return jsonObject;
    }

}


