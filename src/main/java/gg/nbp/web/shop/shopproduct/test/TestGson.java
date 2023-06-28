package gg.nbp.web.shop.shopproduct.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class TestGson {
    public static void main(String[] args) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<>();

        map.put("test1",1);
        map.put("test2","aaa");

        String jspn=gson.toJson(map);
        System.out.println(jspn);


        JsonElement jsonElement = JsonParser.parseString(jspn);

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                JsonElement value = jsonObject.get(key);
                System.out.println(key + ": " + value);
            }
        }
    }
}
