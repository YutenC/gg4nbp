package gg.nbp.web.shop.shoporder.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.shop.shoporder.dao.OrderDetailDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class OrderDetailDaoImple implements OrderDetailDao {

	@PersistenceContext
	Session session;
	
	@Override
	public int insert(OrderDetail orderDetail) {
		session.persist(orderDetail);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		OrderDetail orderDetail = session.get(OrderDetail.class, id);
		session.remove(orderDetail);
		return 1;
	}

	@Override
	public int update(OrderDetail orderDetail) {
		session.update(orderDetail);
		return 1;
	}

	@Override
	public OrderDetail selectById(Integer id) {
		return session.get(OrderDetail.class, id);
	}

	@Override
	public List<OrderDetail> selectAll() {
		return session
				.createQuery("FROM OrderDetail ORDER BY orderId", OrderDetail.class)
				.getResultList();
	}

	@Override
	public OrderDetail selectByCompositePK(Integer productId, Integer orderId) {
		String hql = "FROM OrderDetail WHERE productId = :productId AND orderId = :orderId";
		return session
				.createQuery(hql, OrderDetail.class)
				.setParameter("productId", productId)
				.setParameter("orderId", orderId)
				.uniqueResult();
	}

	@Override
	public OrderDetail selectByManagerId(Integer managerId) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<OrderDetail> criteriaQuery = criteriaBuilder.createQuery(OrderDetail.class);
		Root<OrderDetail> root = criteriaQuery.from(OrderDetail.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("managerId"), managerId));
		return session
				.createQuery(criteriaQuery)
				.uniqueResult();
	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
		String hql = "FROM OrderDetail WHERE pkOrderDeatail.orderId = :orderId";
		return session
				.createQuery(hql, OrderDetail.class)
				.setParameter("orderId", orderId)
				.getResultList();
	}

}
