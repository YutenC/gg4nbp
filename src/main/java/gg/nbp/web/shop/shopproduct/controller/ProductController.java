package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ObjectInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

//@Component
@Component
public class ProductController {

    @Autowired
    ProductService productService;

    public ProductController() {
    }

    public String getAllProduct() {
        List<Product> products = productService.getAllProduct();


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


        return toJson(products);

    }

    public String getProductById(Integer id) {
        Product product = productService.getProductById(id);
        return toJson(product);
    }

    public String getProductByType(Integer type) {
        List<Product> products = productService.getProductByType(type);
        return toJson(products);
    }

    public String getProductByBuyTimes(Integer type) {
        List<Product> products = productService.getProductByBuyTimes(type);
        return toJson(products);
    }

    public String searchProducts(String search) {
        List<Product> products = productService.searchProducts(search);
        return toJson(products);
    }

    public String getProductDetail(Integer id) {
        ProductDetail productDetail = productService.getProductDetail(id);
        productService.saveProductBrowseToRedis(id);
        return toJson(productDetail);
    }


    public String getProductHistory() {

        List<Product> products=  productService.getProductHistory();

        return toJson(products);
    }

    public void addCart(Integer id,Integer memId){
        productService.addCart(id,memId);
    }





    private String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(obj);
    }

}
