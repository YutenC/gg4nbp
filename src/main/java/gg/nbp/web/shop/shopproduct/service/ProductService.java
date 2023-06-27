package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProductById(Integer id);

    List<Product> getProductByType(Integer type);

    List<Product> searchProducts(String search);


    ProductDetail getProductDetail(Integer id);

    List<Product> getProductHistory();

    void saveProductBrowseToRedis(Integer id);

    void addCart(Integer productId,Integer memId);

    int addFollow(Integer id, Integer memId);

    List<Product> getProductByBuyTimes(Integer type);
}
