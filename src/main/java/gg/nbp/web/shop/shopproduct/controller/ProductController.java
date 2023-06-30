package gg.nbp.web.shop.shopproduct.controller;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component
@Component
public class ProductController {

    @Autowired
    ProductService productService;

    public ProductController() {
    }

    public String getAllProductByCondition(Integer memberId, ProductSelect productSelect) {
        List<Product> products = productService.getAllProductByCondition(memberId,productSelect);
        return ConvertJson.toJsonExpose(products);
    }

    public String getAllProduct(Integer memId, Integer limit) {
        List<Product> products = productService.getAllProduct(memId,limit);
        return ConvertJson.toJsonExpose(products);
    }

    public String getAllProductWithIndexImg(Integer memId) {
        List<Product> products = productService.getAllProductWithIndexImg(memId);
        return ConvertJson.toJsonExpose(products);
    }


    public String getProductById(Integer id) {
        Product product = productService.getProductById(id);
        return ConvertJson.toJsonExpose(product);
    }

    public String getProductByType(Integer memId, Integer type) {
        List<Product> products = productService.getProductByType(memId, type);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductByBuyTimes(Map<String, Object> map, Integer type) {
        List<Product> products = productService.getProductByBuyTimes(map, type);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductByBuyTimes(Integer amount, Integer type) {
        List<Product> products = productService.getProductByBuyTimes(amount, type);
        return ConvertJson.toJsonExpose(products);
    }

    public String searchProducts(Integer memId, String search) {
        List<Product> products = productService.searchProducts(memId, search);
        return ConvertJson.toJsonExpose(products);
    }

    public String getProductDetail(Integer memId, Integer id) {
        ProductDetail productDetail = productService.getProductDetail(memId, id);
        productService.saveProductBrowseToRedis(id);
        return ConvertJson.toJsonExpose(productDetail);
    }


    public String getProductHistory() {

        List<Product> products = productService.getProductHistory();

        return ConvertJson.toJsonExpose(products);
    }

    public void addCart(Integer id, Integer memId) {
        productService.addCart(id, memId);
    }


    public String updateProductInfo(Product product) {
        productService.updateProductInfo(product);
        return "";
    }


}
