package gg.nbp.web.shop.shoporder.service;

import java.util.Collection;
import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

public interface OrderDetailService extends CoreService{
	
	List<TransOrderProduct> getOrderDetailByOrderId(Integer orderId);

	List<TransOrderProduct> getCommentContentsByProductId(Integer productId);
	
	Collection<ResOrderDetail> getMemberAllOrderDetail(Integer memberId);
	
	boolean commentProduct(Integer orderId, Integer productId, Integer starNum, String commentContent);
	
}
