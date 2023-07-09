package gg.nbp.web.shop.shopproduct.dao.impl;


import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class CouponDaoImpl extends CoreDaoImpl<Coupon, Integer> implements CouponDao {


    @Override
    public int update(Coupon coupon) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Coupon SET ");

        hql.append("discount = :discount,")
                .append("conditionPrice = :conditionPrice,")
                .append("deadline = :deadline,")
                .append("discountCode = :discountCode ")
                .append("WHERE id = :id");

        @SuppressWarnings("deprecation")
		Query<?> query=session.createQuery(hql.toString());

        return query
                .setParameter("discount",coupon.getDiscount())
                .setParameter("conditionPrice",coupon.getConditionPrice())
                .setParameter("deadline",coupon.getDeadline())
                .setParameter("discountCode",coupon.getDiscountCode())
                .setParameter("id",coupon.getId())
                .executeUpdate();

    }


    @Override
    public Coupon selectByDiscountCode(String discountCode) {
//        Session session = session;//getSession();
//        Coupon coupon = session.get(Coupon.class, discountCode);
//        String hql = "from Coupon  where discountCode = "+discountCode;
//        System.out.println("hql: "+hql);
//        return getSession().createQuery(hql,Coupon.class).getSingleResult();


        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> root = criteriaQuery.from(Coupon.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("discountCode"),discountCode));
        Query<Coupon> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.getSingleResult();


    }
}
