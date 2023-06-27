package gg.nbp.web.shop.shopproduct.redisdao;

import gg.nbp.web.shop.shopproduct.entity.Product;

import java.util.List;

public interface ProductRedisDao {
    void saveProductBrowseToRedis(Product product);
    List<Product> getHistoryProductBrowse();
}
