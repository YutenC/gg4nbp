package gg.nbp.web.SecondHand.buy.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import jakarta.persistence.PersistenceContext;


@Repository
public class SecondHandBuylistDaoimpl implements SecondHandBuylistDao {
	
	@PersistenceContext
	private Session session;

	@Override
	public int insert(SecondhandBuylist buylist) {
		session.persist(buylist);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		SecondhandBuylist buylist = session.get(SecondhandBuylist.class, id);
		session.remove(buylist);
		return 1;
	}

	@Override
	public int update(SecondhandBuylist buylist) {
		session.merge(buylist);
		return 1;
	}

	@Override
	public SecondhandBuylist selectById(Integer id) {
		final String sql = "SELECT * FROM secondhand_buylist where buylist_id = :id  ";
		return session.createNativeQuery(sql, SecondhandBuylist.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public List<SecondhandBuylist> selectAll() {
		final String sql = "SELECT * FROM secondhand_buylist ";
		return session.createNativeQuery(sql, SecondhandBuylist.class).getResultList();
	}

	@Override
	public List<SecondhandBuylist> selectByName(String name) {

		final String sql = "SELECT * FROM secondhand_buylist WHERE Product_name LIKE '%"+name+"%'";
		return session
				.createNativeQuery(sql, SecondhandBuylist.class)
				.getResultList();
	}

}
