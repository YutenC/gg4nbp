package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
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
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductById(Integer id) {
        Product product = productService.getProductById(id);
        return ConvertJson.toJsonExpose(product);
    }

    public String getProductByType(Integer type) {
        List<Product> products = productService.getProductByType(type);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductByBuyTimes(Integer type) {
        List<Product> products = productService.getProductByBuyTimes(type);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductByBuyTimes(Integer amount, Integer type) {
        List<Product> products = productService.getProductByBuyTimes(amount,type);
        return ConvertJson.toJsonExpose(products);
    }

    public String searchProducts(String search) {
        List<Product> products = productService.searchProducts(search);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductDetail(Integer id) {
        ProductDetail productDetail = productService.getProductDetail(id);
        productService.saveProductBrowseToRedis(id);
        return ConvertJson.toJsonExpose(productDetail);
    }


    public String getProductHistory() {

        List<Product> products=  productService.getProductHistory();

        return ConvertJson.toJsonExpose(products);
    }

    public void addCart(Integer id,Integer memId){
        productService.addCart(id,memId);
    }



}
