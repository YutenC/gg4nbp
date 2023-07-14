package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductDetail;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getAllProduct(Integer memId, Integer limit);

    List<Product> getAllProductByCondition(Integer memId, ProductSelect productSelect);

    List<Product> getAllProductWithIndexImg(Integer memId);

    Product getProductById(Integer id);

    List<Product> getProductByType(Integer memId,Integer type);

    ProductImage getProductIndexImg(Integer id);

    List<ProductImage> getProductImgs(Integer id);

    List<Product> searchProducts(Integer memId,String search);


    ProductDetail getProductDetail(Integer memId,Integer id);

    List<Product> getProductHistory();

    void saveProductBrowseToRedis(Integer id);

    void addCart(Integer productId,Integer memId);

    List<Product> getProductByBuyTimes(Map<String,Object> map, Integer type);

    List<Product> getProductByBuyTimes(Integer amount,Integer type);

    int updateProductScore(Product product);
    int updateProductAmountBuyTimes(Product product);

    int updateProductInfo(Product product);
}
