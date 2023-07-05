package gg.nbp.web.SecondHand.buy.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.SecondHand.buy.VO.SecondhandBuyPicture;
import gg.nbp.web.SecondHand.buy.VO.SecondhandBuylist;
import gg.nbp.web.SecondHand.buy.dao.SecondHandBuylistPictureDao;
import jakarta.persistence.PersistenceContext;



@Repository
public class SecondHandBuylistPictureDaoimpl implements SecondHandBuylistPictureDao{
	
	@PersistenceContext
	private Session session;

	@Override
	public int insert(SecondhandBuyPicture pojo) {
		session.persist(pojo);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		session.remove(session.get(SecondhandBuyPicture.class, id));
		return 1;
	}

	@Override
	public int update(SecondhandBuyPicture pojo) {
		session.merge("SecondHandBuyPicture", pojo);
		return 1;
	}
	
	public int deleteByListId(Integer id) {
		final String sql = "DELETE FROM secondhand_buy_picture WHERE  BuyList_id = :id";
		session.createNativeQuery(sql, SecondhandBuyPicture.class)
			   .setParameter("id", id)
			   .executeUpdate();
		return 1;
	}
	
	
	@Override
	public int update(SecondhandBuylist sl) {
		deleteByListId(sl.getBuylistId());
		sl.getImage().stream()
					 .forEach(sp ->{
						 sp.setBuylistId(sl.getBuylistId());
						 session.persist(sp);
					 });
		return 1;
	}

	@Override
	public SecondhandBuyPicture selectById(Integer id) {
		final String sql = "SELECT * FROM Secondhand_Buy_Picture where image_id = :id  ";
		return session.createNativeQuery(sql, SecondhandBuyPicture.class)
					  .setParameter("id", id)
					  .getSingleResult();
	}

	@Override
	public List<SecondhandBuyPicture> selectAll() {
		final String sql = "SELECT * FROM Secondhand_Buy_Picture ";
		return session.createNativeQuery(sql, SecondhandBuyPicture.class)
					  .getResultList();
	}

	@Override
	public List<SecondhandBuyPicture> selectBylistId(Integer id) {
		final String sql = "SELECT * FROM Secondhand_Buy_Picture where buylist_id = :id  ";
		return session
				.createNativeQuery(sql, SecondhandBuyPicture.class)
				.setParameter("id", id)
				.getResultList();
	}

}
