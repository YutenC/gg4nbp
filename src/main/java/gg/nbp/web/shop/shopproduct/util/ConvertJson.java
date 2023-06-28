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
