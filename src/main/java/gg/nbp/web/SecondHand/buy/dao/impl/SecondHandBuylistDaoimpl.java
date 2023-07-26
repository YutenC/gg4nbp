package gg.nbp.web.SecondHand.buy.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistDao;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistPictureDao;
import jakarta.persistence.PersistenceContext;


@Repository
public class SecondHandBuylistDaoimpl implements SecondHandBuylistDao {

	@PersistenceContext
	private Session session;
	@Autowired
	private SecondHandBuylistPictureDao daoPic;

	@Override
	public int insert(SecondhandBuylist buylist) {
		session.persist(buylist);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		daoPic.selectBylistId(id).stream()
								 .forEach(el -> daoPic.deleteById(el.getImageId()));
		session.remove(session.get(SecondhandBuylist.class, id));
		return 1;
	}

	@Override
	public int update(SecondhandBuylist buylist) {
		daoPic.update(buylist);
		session.merge("SecondhandBuylist", buylist);
		return 1;
	}

	@Override
	public SecondhandBuylist selectById(Integer id) {
		final String sql = "SELECT * FROM secondhand_buylist where buylist_id = :id  ";
		return selectImage(
				session.createNativeQuery(sql, SecondhandBuylist.class)
					   .setParameter("id", id)
					   .getSingleResult());
	}

	@Override
	public List<SecondhandBuylist> selectByMemberId(Integer id) {
		final String sql = "SELECT * FROM secondhand_buylist where Member_id = :id  ";
		return selectImages(
				session.createNativeQuery(sql, SecondhandBuylist.class)
					   .setParameter("id", id)
					   .getResultList());
	}

	@Override
	public List<SecondhandBuylist> selectByName4Member(String Name, Integer id) {
		final String sql = "SELECT * FROM ( SELECT * FROM secondhand_buylist where Member_id = :id ) ssid WHERE Product_name LIKE '%"
				+ Name + "%'";
		return selectImages(
				session.createNativeQuery(sql, SecondhandBuylist.class)
					   .setParameter("id", id)
					   .getResultList());
	}

	@Override
	public List<SecondhandBuylist> selectAll() {
		final String sql = "SELECT * FROM secondhand_buylist ";
		return selectImages(
				session.createNativeQuery(sql, SecondhandBuylist.class)
					   .getResultList());
	}

	@Override
	public List<SecondhandBuylist> selectByName(String name) {
		final String sql = "SELECT * FROM secondhand_buylist WHERE Product_name LIKE '%" + name + "%'";
		return selectImages(
				session.createNativeQuery(sql, SecondhandBuylist.class)
					   .getResultList());
	}

	
	private SecondhandBuylist selectImage(SecondhandBuylist sl) {
		sl.setImage(daoPic.selectBylistId(sl.getBuylistId()));
		return sl;
	}

	private List<SecondhandBuylist> selectImages(List<SecondhandBuylist> list) {
		list.stream()
			.forEach(el -> el.setImage(daoPic.selectBylistId(el.getBuylistId())));
		return list;
	}
}
