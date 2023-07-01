package gg.nbp.web.shop.shopproduct.test;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        new Test().run();


    }

    void run() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String key="productIndexImage";
        Product product=new Product();
        product.setProductIndexImage(new ProductImage());

        Field field= Product.class.getDeclaredField(key);

        field.setAccessible(true);
        Object o= field.get(product);

        String first= key.substring(0,1).toUpperCase();
        String newStr="set"+first+key.substring(1);
        System.out.println(newStr);

        Method method= Test.class.getDeclaredMethod(newStr,String.class);
        method.setAccessible(true);
        method.invoke(this,"ss");
    }

    void setProductIndexImage(String ss){
        System.out.println(ss);
    }
}
