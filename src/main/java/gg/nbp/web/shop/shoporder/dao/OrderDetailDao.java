package gg.nbp.web.shop.shoporder.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import gg.nbp.web.shop.shoporder.entity.PKOrderDetail;

public interface OrderDetailDao extends CoreDao<OrderDetail, Integer> {

	List<OrderDetail> selectByOrderId(Integer orderId);
	
	List<OrderDetail> selectByProductId(Integer productId);
	
	OrderDetail selectByManagerId(Integer managerId); // 找負責退貨審核的管理員
	
	OrderDetail selectByPKOrderDetail(PKOrderDetail pkod);
}
