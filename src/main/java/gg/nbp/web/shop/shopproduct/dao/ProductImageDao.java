package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.shop.shopproduct.core.CoreDao;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;

import java.util.List;

public interface ProductImageDao extends CoreDao<ProductImage,Integer> {
    List<ProductImage> selectByProductId(Integer id);
    ProductImage getIndexImgByProductId(Integer id);
}
