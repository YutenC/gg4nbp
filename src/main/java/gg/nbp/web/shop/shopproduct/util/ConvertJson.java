package gg.nbp.web.shop.shopproduct.util;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertJson {

    public static String toJsonExpose(Object obj) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(obj);
    }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    public static Map<String, JsonElement> getJsonDataMap(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        Map<String, JsonElement> map = new HashMap<>();
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                JsonElement value = jsonObject.get(key);
                map.put(key, value);
            }
        }
        return map;
    }

    public static <T> List<T> getFromArray(JsonElement jsonElement, Class<T> tClass) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<T> list = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement jsonElement1 : jsonArray) {
            T ob = gson.fromJson(jsonElement1, tClass);
            list.add(ob);
        }
        return list;
    }


}
