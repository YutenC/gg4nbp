package gg.nbp.web.shop.shopproduct.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyUtil {

    public static boolean checkNULL(String key,Object source,Class<?> class_)  {
        Field field= null;
        try {
            field = class_.getDeclaredField(key);
            field.setAccessible(true);
            return field.get(source) == null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runMethod(String key,Object source,Class<?> class_,Class<?> parameterType, Object arg){
        String first= key.substring(0,1).toUpperCase();
        String newStr="set"+first+key.substring(1);
//        System.out.println(newStr);

        Method method= null;
        try {
            method = class_.getDeclaredMethod(newStr,parameterType);
            method.setAccessible(true);
            method.invoke(source,arg);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
