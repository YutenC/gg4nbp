package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundFactory;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundHandler;
import gg.nbp.web.shop.shopproduct.pojo.RequestMsg;
import org.springframework.stereotype.Component;

@Component
public class BackgroundMessageController {


    public String getBackgroundMessage(String taskName) {
        BackgroundHandler backgroundHandler = BackgroundFactory.getBackgroundHandler("productBackground");
        String str = backgroundHandler.getTaskResult(taskName);
        System.out.println("str: " + str);
        Gson gson = new Gson();
        RequestMsg requestMsg = new RequestMsg("longTime", "longTimeProcess", "");

        return gson.toJson(requestMsg);
    }
}
