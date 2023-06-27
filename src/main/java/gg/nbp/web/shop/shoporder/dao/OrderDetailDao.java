package gg.nbp.web.shop.shoporder.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;

public interface OrderDetailDao extends CoreDao<OrderDetail, Integer> {

	OrderDetail selectByCompositePK(Integer productId, Integer orderId);
	
	List<OrderDetail> selectByOrderId(Integer orderId);
	
	OrderDetail selectByManagerId(Integer managerId); // 找負責退貨審核的管理員
}
