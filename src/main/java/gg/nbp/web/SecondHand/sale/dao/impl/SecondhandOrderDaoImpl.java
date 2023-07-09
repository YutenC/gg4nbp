package gg.nbp.web.SecondHand.sale.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.SecondHand.sale.dao.SecondhandOrderDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import jakarta.persistence.PersistenceContext;

@Repository
public class SecondhandOrderDaoImpl implements SecondhandOrderDao {

    @PersistenceContext
    private Session session;

    @Override
    public int insert(SecondhandOrder pojo) {
        session.persist(pojo);
        return 1;
    }

    @Override
    public int deleteById(Integer orderId) {
        @SuppressWarnings("deprecation")
		SecondhandOrder pojo = session.load(SecondhandOrder.class, orderId);
        session.remove(pojo);
        return 1;
    }

    @Override
    public int update(SecondhandOrder pojo) {
        session.merge(pojo);
        return 1;
    }

    @Override
    public SecondhandOrder selectById(Integer orderId) {
        SecondhandOrder result = session.get(SecondhandOrder.class, orderId);
        return result;
    }

    @Override
    public List<SecondhandOrder> selectAll() {
        final String hql = "FROM SecondhandOrder ORDER BY orderId";
        List<SecondhandOrder> result = session.createQuery(hql, SecondhandOrder.class).getResultList();
        return result;
    }

    @Override
    public List<SecondhandOrder> selectByMemId(Integer memberId) {

        final String hql = "SELECT od FROM SecondhandOrder od where od.memberId = :memberId";
        List<SecondhandOrder> result = session.createQuery(hql, SecondhandOrder.class)
                .setParameter("memberId", memberId)
                .getResultList();
        return result;

    }


}
