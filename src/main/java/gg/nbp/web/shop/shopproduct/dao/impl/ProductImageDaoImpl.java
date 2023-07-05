package gg.nbp.web.shop.shopproduct.dao.impl;

import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductImageDaoImpl extends CoreDaoImpl<ProductImage, Integer> implements ProductImageDao {
    @Override
    public int update(ProductImage productImage) {
        return 1;
    }

    @Override
    public List<ProductImage> selectByProductId(Integer id) {
        String hql = "from ProductImage where product.id = " + id;
        return (List<ProductImage>) session.createQuery(hql, ProductImage.class).getResultList();
    }

//    @Override
//    public ProductImage getIndexImgByProductId(Integer id) {
//        String hql = "from ProductImage where product.id = " + id + " and image like '%index%'";
//        ProductImage productImage= (ProductImage) session.createQuery(hql, ProductImage.class).getSingleResult();
//        return productImage;
//    }

    @Override
    public List<ProductImage> getIndexImgByProductId(Integer id) {
        String hql = "from ProductImage where product.id = " + id + " and image like '%index%'";
        List<ProductImage> productImages= (List<ProductImage>) session.createQuery(hql, ProductImage.class).getResultList();
        return productImages;
    }
}
