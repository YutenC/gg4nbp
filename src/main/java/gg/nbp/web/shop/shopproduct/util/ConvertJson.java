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

//public static<T> T cc(String result,Class<T> ){
//    Gson gson = new Gson();
//    T obj = gson.fromJson(result, classOfT);
//}

    public static Map<String, JsonElement> getJsonDataMap(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        Map<String, JsonElement> map = new HashMap<>();
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                JsonElement value = jsonObject.get(key);
                map.put(key, value);
//                System.out.println(key + ": " + value);
            }
        }
        return map;
    }

    public static <T> List<T> getFromArray(JsonElement jsonElement, Class<T> tClass) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<T> list = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement jsonElement1 : jsonArray) {
//            jsonElement1.getAsJsonObject();
            T ob = gson.fromJson(jsonElement1, tClass);

            list.add(ob);
        }
        return list;
    }

    //        ExclusionStrategy strategy = new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes field) {
//                if (field.getDeclaringClass() == MyClass.class && field.getName().equals("other")) {
//                    return true;
//                }
//                if (field.getDeclaringClass() == MySubClass.class && field.getName().equals("otherVerboseInfo")) {
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        };


//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//        String jsonString = gson.toJson(products);
}
