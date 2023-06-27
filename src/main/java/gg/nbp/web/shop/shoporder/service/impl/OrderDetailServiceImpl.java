package gg.nbp.web.shop.shoporder.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.product.dao.ProductDao;
import com.shop.product.dao.impl.ProductDaoImpl;
import com.shop.product.entity.Product;

import gg.nbp.web.shop.shoporder.dao.OrderDetailDao;
import gg.nbp.web.shop.shoporder.dao.OrderMasterDao;
import gg.nbp.web.shop.shoporder.dao.impl.OrderDetailDaoImple;
import gg.nbp.web.shop.shoporder.dao.impl.OrderMasterDaoImpl;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import jakarta.transaction.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	@Autowired
	private OrderDetailDao odDao;
	@Autowired
	private OrderMasterDao omDao;
	@Autowired
	private ProductDao pdDao;
	
	@Transactional
	@Override
	public List<TransOrderProduct> getOrderDetailByOrderId(Integer orderId) {
		try {
			List<TransOrderProduct> odProdcuts = new ArrayList<>();
			List<OrderDetail> orderDetails = odDao.selectByOrderId(orderId);
			for (OrderDetail od : orderDetails) {
				Product pd = pdDao.selectById(od.getPkOrderDeatail().getProductID());
				TransOrderProduct trPd = new TransOrderProduct();
				
				trPd.setBrand(pd.getBrand());
				trPd.setBuyAmount(od.getQuantity());
				trPd.setChecked(true);
				trPd.setPrice(pd.getPrice());
				trPd.setProductId(od.getPkOrderDeatail().getProductID());
				trPd.setProductName(pd.getProductName());
				if (pd.getPoImages().isEmpty()) {
					trPd.setProductImgUrl(null);
				} else {
					trPd.setProductImgUrl(pd.getPoImages().get(0).getImage());
				}
				trPd.setStockAmount(pd.getAmount());
				
				odProdcuts.add(trPd);
			}
			
			return odProdcuts;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public Collection<ResOrderDetail> getMemberAllOrderDetail(Integer memberId) {
		try {
			List<OrderMaster> omList = omDao.selectByMemberId(memberId);
			List<ResOrderDetail> resODList = new ArrayList<>();
			Map<Integer, ResOrderDetail> checkMap = new HashMap<>();
			
			for (OrderMaster om : omList) {
				List<OrderDetail> odList = odDao.selectByOrderId(om.getOrderId());
				for (OrderDetail od : odList) {
					ResOrderDetail rsOD = new ResOrderDetail();
					rsOD.setProductAmount(od.getQuantity());
					rsOD.setProductId(od.getPkOrderDeatail().getProductID());
					rsOD.setPurchaseDate(om.getCommitDate());
					Product pd = pdDao.selectById(od.getPkOrderDeatail().getProductID());
					rsOD.setProductName(pd.getProductName());
					rsOD.setProductPrice(pd.getPrice());
					ResOrderDetail checkUnit = (ResOrderDetail)checkMap.get(pd.getProductId());
					if (checkUnit == null ||
							(checkUnit.getPurchaseDate().getTime() < rsOD.getPurchaseDate().getTime())) { 
						checkMap.put(pd.getProductId(), rsOD);
					} 
				}
			}
			return checkMap.values();
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
