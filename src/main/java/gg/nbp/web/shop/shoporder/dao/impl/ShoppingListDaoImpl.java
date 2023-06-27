package gg.nbp.web.shop.shoporder.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gg.nbp.web.shop.shoporder.dao.ShoppingListDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import jakarta.persistence.PersistenceContext;

@Repository
public class ShoppingListDaoImpl implements ShoppingListDao {

	@PersistenceContext
	Session session;
	
	@Override
	public int insert(ShoppingList shoppingList) {
		session.persist(shoppingList);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		session.remove(selectById(id));
		return 1;
	}

	@Override
	public int update(ShoppingList shoppingList) {
		session.update(shoppingList);
		return 1;
	}

	@Override
	public ShoppingList selectById(Integer id) {
		return session.get(ShoppingList.class, id);
	}

	@Override
	public List<ShoppingList> selectAll() {
		return session
				.createQuery("FROM ShoppingList ORDER BY memberId", ShoppingList.class)
				.getResultList();
	}

	@Override
	public ShoppingList selectByCompositePk(PKShoppingList pksplist) {
		return session.get(ShoppingList.class, pksplist);
	}

	@Override
	public List<ShoppingList> selectByMemberId(Integer memberId) {
		String hql = "From ShoppingList WHERE pkShoppingList.memberId =: memberId";
		return session.
				createQuery(hql, ShoppingList.class)
				.setParameter("memberId", memberId)
				.getResultList();
	}

}
