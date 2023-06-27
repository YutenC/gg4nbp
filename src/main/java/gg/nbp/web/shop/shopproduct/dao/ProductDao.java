package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.shop.shopproduct.core.CoreDao;
import gg.nbp.web.shop.shopproduct.entity.Product;

import java.util.List;

public interface ProductDao extends CoreDao<Product,Integer> {

    List<Product> selectByType(String type);

    List<Product> selectByBuyTimes(String type);

    List<Product> selectByBuyTimes(Integer limit, String type);

    List<Product> searchProducts(String search);

    int updateProductScore(Product product);

    int updateProductAmountBuyTimes(Product product);
}
