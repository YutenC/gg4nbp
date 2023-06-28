package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProductById(Integer id);

    List<Product> getProductByType(Integer type);

    ProductImage getProductIndexImg(Integer id);

    List<ProductImage> getProductImgs(Integer id);

    List<Product> searchProducts(String search);


    ProductDetail getProductDetail(Integer id);

    List<Product> getProductHistory();

    void saveProductBrowseToRedis(Integer id);

    void addCart(Integer productId,Integer memId);



    List<Product> getProductByBuyTimes(Integer type);

    List<Product> getProductByBuyTimes(Integer amount,Integer type);


    int updateProductScore(Product product);
    int updateProductAmountBuyTimes(Product product);
}
