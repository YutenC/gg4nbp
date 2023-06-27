package gg.nbp.web.shop.shopproduct.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
}
