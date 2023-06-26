package gg.nbp.web.shop.shopproduct.dao.impl;

import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl extends CoreDaoImpl<Product,Integer> implements ProductDao {

    @PersistenceContext
    private Session session;

    @Override
    public int update(Product product) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Product SET ");

        final String productName = product.getProductName();

        hql.append("productName = :productName,")
                .append("type = :type,")
                .append("price = :price,")
                .append("amount = :amount,")
                .append("buyTimes = :buyTimes,")
                .append("brand = :brand,")
                .append("rate = :rate,")
                .append("revieweCount = :revieweCount,")
                .append("content = :content,")
                .append("launchTime = NOW() ")
                .append("WHERE id = :id");

        Query<?> query=session.createQuery(hql.toString());


        return query
                .setParameter("productName",product.getProductName())
                .setParameter("type",product.getType())
                .setParameter("price",product.getPrice())
                .setParameter("amount",product.getAmount())
                .setParameter("buyTimes",product.getBuyTimes())
                .setParameter("brand",product.getBrand())
                .setParameter("rate",product.getRate())
                .setParameter("revieweCount",product.getRevieweCount())
                .setParameter("content",product.getContent())
                .setParameter("launchTime",product.getLaunchTime())
                .setParameter("id",product.getId())
                .executeUpdate();

    }


    @Override
    public List<Product> selectByType(String type) {
        String hql = "from Product  where type = "+type;
        System.out.println("hql: "+hql);
        return session.createQuery(hql, Product.class).getResultList();
    }

    @Override
    public List<Product> selectByBuyTimes(String type) {
        String hql;
        if("".equals(type)||"0".equals(type)){
            hql = "from Product "+"order by buyTimes DESC";
        }
        else {
            hql = "from Product  where type = "+type +"order by buyTimes DESC";
        }

        System.out.println("hql: "+hql);
        return session.createQuery(hql, Product.class).getResultList();
    }

    @Override
    public List<Product> searchProducts(String search) {
        String hql = "from Product  where productName like '%"+search+"%'";
        System.out.println("hql: "+hql);
        return session.createQuery(hql, Product.class).getResultList();
    }


}
