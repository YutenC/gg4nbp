package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundFactory;
import gg.nbp.web.shop.shopproduct.common.backgroundtask.BackgroundHandler;
import gg.nbp.web.shop.shopproduct.pojo.ResponseMsg;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import org.springframework.stereotype.Component;

@Component
public class BackgroundMessageController {


    public String getBackgroundMessage(String taskName) {
        BackgroundHandler backgroundHandler = BackgroundFactory.getBackgroundHandler("shopProductBackground");
        String str = backgroundHandler.getTaskResult(taskName);
        System.out.println("str: " + str);

        ResponseMsg requestMsg ;

        if("ok".equals(str)){
            requestMsg = new ResponseMsg("ok", "", "");
        }
        else {
            requestMsg = new ResponseMsg("longTime", "longTimeProcess", "");
        }

        return ConvertJson.toJson(requestMsg);
    }
}
