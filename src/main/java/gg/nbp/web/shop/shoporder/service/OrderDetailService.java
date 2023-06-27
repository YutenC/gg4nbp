package gg.nbp.web.shop.shoporder.service;

import java.util.Collection;
import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

public interface OrderDetailService extends CoreService{
	
	List<TransOrderProduct> getOrderDetailByOrderId(Integer orderId);
	
	Collection<ResOrderDetail> getMemberAllOrderDetail(Integer memberId);
}
