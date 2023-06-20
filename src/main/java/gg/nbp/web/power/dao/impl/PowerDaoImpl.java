package gg.nbp.web.power.dao.impl;

import java.util.List;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.power.dao.PowerDao;
//import com.manager.entity.Manager;
import gg.nbp.web.power.entity.Power;

@Repository
public class PowerDaoImpl implements PowerDao {
	
	@PersistenceContext
	private Session session;
	
	@Override
	public int insert(Power power) {
		session.persist(power);
		
		return 1;
	}
	
	@Override
	public int deleteById(Integer power_id) {
		Power power= session.load(Power.class, power_id);
		session.remove(power);
		
		return 1;
	}
	
	@Override
	public int update(Power power) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Power SET ");	
		hql.append("power_name = :power_name,")
			.append("power_content = :power_content,")
			.append("WHERE power_id = :power_id");
			
		Query query = session.createQuery(hql.toString());
		return query.setParameter("power_name", power.getPower_name())
				.setParameter("power_content", power.getPower_content())
				.setParameter("power_id", power.getPower_id())
				.executeUpdate();
	}
	
	@Override
	public Power selectById(Integer power_id) {
		
		return session.get(Power.class, power_id);
	}
	
	@Override
	public List<Power> selectAll() {
		final String hql = "FROM Power ORDER BY power_id";
		return session
				.createQuery(hql, Power.class)
				.getResultList();
	}
	
	@Override
	public Power selectByPowerName(String power_name) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Power> criteriaQuery = criteriaBuilder.createQuery(Power.class);
		Root<Power> root = criteriaQuery.from(Power.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("power_name"), power_name));
		return session.createQuery(criteriaQuery).uniqueResult();
	}
	
}
