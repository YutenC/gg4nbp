package gg.nbp.web.shop.shopproduct.dao.impl;


import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

//import org.hibernate.query.Query;
//import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
public class CouponDaoImpl extends CoreDaoImpl<Coupon, Integer> implements CouponDao {

    @Override
    @Transactional
    public int update(Coupon coupon) {
        final StringBuilder hql = new StringBuilder()
                .append("UPDATE Coupon SET ");

        hql.append("discount = :discount,")
                .append("conditionPrice = :conditionPrice,")
                .append("deadline = :deadline,")
                .append("discountCode = :discountCode, ")
                .append("state = :state ")
                .append("WHERE id = :id");

        Query<?> query = session.createQuery(hql.toString());


        return query
                .setParameter("discount", coupon.getDiscount())
                .setParameter("conditionPrice", coupon.getConditionPrice())
                .setParameter("deadline", coupon.getDeadline())
                .setParameter("discountCode", coupon.getDiscountCode())
                .setParameter("state", coupon.getState())
                .setParameter("id", coupon.getId())
                .executeUpdate();
    }


    @Override
    public Coupon selectByDiscountCode(String discountCode) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> root = criteriaQuery.from(Coupon.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("discountCode"), discountCode));
        Query<Coupon> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public Coupon selectByDiscountCodeByManager(String discountCode) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> root = criteriaQuery.from(Coupon.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("discountCode"), discountCode));
        Query<Coupon> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public List<Coupon> selectByCondition(DaoConditionSelect daoSelect) {

        String conditionStr = "";

        if (daoSelect.getConditions() != null) {
            for (int i = 0; i < daoSelect.getConditions().size(); i++) {
                if (i == 0) {
                    conditionStr += " where ";
                } else {
                    conditionStr += " AND ";
                }
                ProductSelect.Condition condition = daoSelect.getConditions().get(i);

                if (condition.getAction() == null || "=".equals(condition.getAction())) {
                    conditionStr += " " + condition.getKey() + " = " + condition.getValue() + " ";
                } else if ("like".equals(condition.getAction())) {
                    conditionStr += " " + condition.getKey() + " like '%" + condition.getValue() + "%' ";
                }

            }
        }

        String hql = "from Coupon  " + conditionStr;

        Integer limit = -1;
        Integer offset = -1;
        String hql2 = "";
        if (daoSelect.getSqlConditions() != null) {
            for (int i = 0; i < daoSelect.getSqlConditions().size(); i++) {
                ProductSelect.Condition condition = daoSelect.getSqlConditions().get(i);
                if ("limit".equals(condition.getKey())) {
                    Object oValue = condition.getValue();
                    limit = Integer.valueOf((String) oValue);
//                    limit = ((Double) oValue).intValue();
                } else if ("offset".equals(condition.getKey())) {
                    Object oValue = condition.getValue();
                    offset = ((Double) oValue).intValue();
                }
            }
        }


        String hql3 = "";
        if (daoSelect.getSort() != null) {
            ProductSelect.Condition condition = daoSelect.getSort();

            if ("order".equalsIgnoreCase(condition.getAction())) {
                if (condition.getKey() == null || "".equals(condition.getKey()) || "asc".equalsIgnoreCase(condition.getKey())) {
                    hql3 = "order by " + condition.getValue() + " ";
                } else if ("desc".equalsIgnoreCase(condition.getKey())) {
                    hql3 = "order by " + condition.getValue() + " " + "DESC";
                }
            }

        }

        hql = hql + hql2 + hql3;
        System.out.println(hql);
        Query<Coupon> query;
        if (limit == -1) {
            query = session.createQuery(hql, Coupon.class);
        } else {
            query = session.createQuery(hql, Coupon.class).setMaxResults(limit);
        }

        if (offset != -1) {
            query.setFirstResult(offset);
        }
        return query.getResultList();
    }

}
