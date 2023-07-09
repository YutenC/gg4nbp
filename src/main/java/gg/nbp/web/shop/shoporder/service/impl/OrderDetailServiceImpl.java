package gg.nbp.web.shop.shoporder.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.nbp.web.shop.shoporder.dao.OrderDetailDao;
import gg.nbp.web.shop.shoporder.dao.OrderMasterDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.entity.PKOrderDeatail;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.util.ResOrderDetail;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import jakarta.transaction.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	@Autowired
	private OrderDetailDao odDao;
	@Autowired
	private OrderMasterDao omDao;
	@Autowired
	private ProductDao pdDao;
	@Autowired
	private ProductService pService;
	
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
				Integer comment = od.getComment();
				if (comment != null) {
					trPd.setComment(comment);
				}
				String commentContnet = od.getCommentContent();
				if (commentContnet != null) {
					trPd.setCommentContent(od.getCommentContent());
				}
				if (pService.getProductIndexImg(pd.getId()) == null) {
					trPd.setProductImgUrl(null);
				} else {
					trPd.setProductImgUrl(pService.getProductIndexImg(pd.getId()).getImage());
				}
				trPd.setStockAmount(pd.getAmount());
				
				odProdcuts.add(trPd);
			}
			
			return odProdcuts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public Collection<ResOrderDetail> getMemberAllOrderDetail(Integer memberId) {
		try {
			List<OrderMaster> omList = omDao.selectByMemberId(memberId);
//			List<ResOrderDetail> resODList = new ArrayList<>();
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
					ResOrderDetail checkUnit = (ResOrderDetail)checkMap.get(pd.getId());
					if (checkUnit == null ||
							(checkUnit.getPurchaseDate().getTime() < rsOD.getPurchaseDate().getTime())) { 
						checkMap.put(pd.getId(), rsOD);
					} 
				}
			}
			return checkMap.values();
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public boolean commentProduct(Integer orderId, Integer productId, Integer starNum, String commentContent) {
		try {
			PKOrderDeatail pkod = new PKOrderDeatail(productId, orderId);
			OrderDetail od = odDao.selectByPKOrderDetail(pkod);
			od.setComment(starNum);
			od.setCommentContent(commentContent);
			odDao.update(od);
			
			Product pd = pdDao.selectById(productId);
			pd.setRate(pd.getRate() + starNum);
			pd.setRevieweCount(pd.getRevieweCount()+1); // 每次收到評價就增加一次評分人數
			pdDao.update(pd);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
