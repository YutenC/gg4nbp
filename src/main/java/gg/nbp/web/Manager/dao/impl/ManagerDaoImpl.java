package gg.nbp.web.Manager.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.Manager.dao.ManagerDao;
import gg.nbp.web.Manager.entity.Manager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ManagerDaoImpl implements ManagerDao{ 
	
	@PersistenceContext
	private Session session ;
	
	@Override
	public int insert(Manager manager) {
		session.persist(manager);
		
		return 1;
	}
	
	@Override
	public int deleteById(Integer manager_id) {
		Manager manager= session.load(Manager.class, manager_id);
		session.remove(manager);
		
		return 1;
	}
	
	@Override
	public int update(Manager manager) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Manager SET ");
		final String password = manager.getPassword();
		if (password != null && !password.isEmpty()) {
			hql.append("password = :password,");
		}
		hql.append("account = :account,")
			.append("name = :name,")
			.append("email = :email,")
			.append("phone = :phone,")
			.append("address = :address,")
			.append("is_working = :is_working ")
			.append("WHERE manager_id = :manager_id");
			
		Query query = session.createQuery(hql.toString());
		if (password != null && !password.isEmpty()) {
			query.setParameter("password", manager.getPassword());
		}
		return query.setParameter("name", manager.getName())
				.setParameter("email", manager.getEmail())
				.setParameter("phone", manager.getPhone())
				.setParameter("address", manager.getAddress())
				.setParameter("is_working", manager.getIs_working())
				.setParameter("account", manager.getAccount())
				.setParameter("manager_id", manager.getManager_id())
				.executeUpdate();
	}
	
	@Override
	public Manager selectById(Integer manager_id) {
		
		return session.get(Manager.class, manager_id);
	}
	
	@Override
	public List<Manager> selectAll() {
		final String hql = "FROM Manager ORDER BY manager_id";
		return session
				.createQuery(hql, Manager.class)
				.getResultList();
	}
	
	@Override
	public Manager selectByUserName(String account) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Manager> criteriaQuery = criteriaBuilder.createQuery(Manager.class);
		Root<Manager> root = criteriaQuery.from(Manager.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("account"), account));
		return session.createQuery(criteriaQuery).uniqueResult();
	}
	
	@Override
	public Manager selectForLogin(String account, String password) {
		final String sql = "select * from MANAGER "
				+ "where ACCOUNT = :account and PASSWORD = :password";
		return session.createNativeQuery(sql, Manager.class)
				.setParameter("account", account)
				.setParameter("password", password)
				.uniqueResult();
	}
	
}
