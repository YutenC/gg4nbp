package gg.nbp.web.ban.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.ban.dao.BanDao;
import gg.nbp.web.ban.entity.Ban;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class BanDaoImpl implements BanDao{
	
	@PersistenceContext
	private Session session;
	
	@Override
	public int insert(Ban ban) {
		session.persist(ban);
		return 1;
	}
	
	@Override
	public int deleteById(Integer ban_id) {
		Ban ban= session.load(Ban.class, ban_id);
		session.remove(ban);
		
		return 1;
	}
	
	@Override
	public int update(Ban ban) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Ban SET ");
		
		hql.append("member_id = :member_id,")
			.append("manager_id = :manager_id,")
			.append("ban_reason = :ban_reason,")
			.append("startTime = :startTime,")
			.append("endTime = :endTime,")
			.append("WHERE ban_id = :ban_id");
			
		Query query = session.createQuery(hql.toString());
		return query.setParameter("member_id", ban.getMember_id())
				.setParameter("manager_id", ban.getManager_id())
				.setParameter("ban_reason", ban.getBan_reason())
				.setParameter("startTime", ban.getStartTime())
				.setParameter("endTime", ban.getEndTime())
				.setParameter("ban_id", ban.getBan_id())
				.executeUpdate();
	}
	
	@Override
	public Ban selectById(Integer ban_id) {
		return session.get(Ban.class, ban_id);
	}
	
	@Override
	public List<Ban> selectAll() {
		final String hql = "FROM Ban ORDER BY ban_id";
		return session
				.createQuery(hql, Ban.class)
				.getResultList();
	}

	@Override
	public Ban selectByMember(Integer member_id) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Ban> criteriaQuery = criteriaBuilder.createQuery(Ban.class);
		Root<Ban> root = criteriaQuery.from(Ban.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member_id"), member_id));
		return session.createQuery(criteriaQuery).uniqueResult();
	}
	
	@Override
	public Ban selectByManager(Integer manager_id) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Ban> criteriaQuery = criteriaBuilder.createQuery(Ban.class);
		Root<Ban> root = criteriaQuery.from(Ban.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("manager_id"), manager_id));
		return session.createQuery(criteriaQuery).uniqueResult();
	}
	
}
