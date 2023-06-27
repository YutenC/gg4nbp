package gg.nbp.web.shop.followlist.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.shop.followlist.dao.FollowListDao;
import gg.nbp.web.shop.followlist.entity.FollowList;
import jakarta.persistence.PersistenceContext;

@Repository
public class FollowListDaoImpl implements FollowListDao {

	@PersistenceContext
	Session session;
	
	@Override
	public int insert(FollowList followList) {
		session.persist(followList);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
//		getSession().remove(getSession().get(FollowList.class, id));
		return 0;
	}

	@Override
	public boolean deleteByCompositePK(FollowList flist) {
		session.remove(flist);
		return true;
	}

	@Override
	public int update(FollowList followList) {
		session.update(followList);
		return 1;
	}

	@Override
	public FollowList selectById(Integer id) {
		return null; //getSession().get(FollowList.class, id);
	}

	@Override
	public List<FollowList> selectAll() {
		String hql = "FROM FollowList ORDER BY productId";
		return session.createQuery(hql, FollowList.class).getResultList();
	}

	@Override
	public List<FollowList> selectByMemeberId(Integer memberId) {
		String hql = "FROM FollowList WHERE pkFollowList.memberId = :memberId";
		return session.createQuery(hql, FollowList.class).setParameter("memberId", memberId).getResultList();
	}
	
}
