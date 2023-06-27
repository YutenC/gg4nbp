package gg.nbp.web.shop.shoporder.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.dao.JedisOrderMasterDao;
import gg.nbp.web.shop.shoporder.dao.OrderDetailDao;
import gg.nbp.web.shop.shoporder.dao.OrderMasterDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.entity.PKOrderDeatail;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.ManageOrder;
import gg.nbp.web.shop.shoporder.util.MemberViewOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import jakarta.transaction.Transactional;

@Service
public class OrderMasterServiceImpl implements OrderMasterService{
	
	@Autowired
	private OrderMasterDao omdao;
	@Autowired
	private OrderDetailDao odDao;
	@Autowired
	private ProductDao pdDao;
	@Autowired
	private MemberDao mbDao;
	@Autowired
	private CouponDao cpDao;
	@Autowired
	private JedisOrderMasterDao jdOmDao;
	@Autowired
	private ProductService productService;
	
	private Gson gson;
	
	public OrderMasterServiceImpl() {
		this.gson = new Gson();
	}

	@Transactional
	@Override
	public boolean newOrder(OrderMaster om, List<TransOrderProduct> trObjList, Member member) {
		try {
			omdao.insert(om);
			Integer ordeId = om.getOrderId();
			
			Integer prodcutTotalPrice = 0;
			for (TransOrderProduct trObj : trObjList) {
				PKOrderDeatail pkod = new PKOrderDeatail();
				pkod.setOrderId(ordeId);
				pkod.setProductID(trObj.getProductId());
				
				OrderDetail od = new OrderDetail();
				od.setPkOrderDeatail(pkod);
				od.setQuantity(trObj.getBuyAmount());
				Product checkProduct = pdDao.selectById(trObj.getProductId());
				od.setTotalPrice(trObj.getBuyAmount() * checkProduct.getPrice());
				
				prodcutTotalPrice += trObj.getBuyAmount() * checkProduct.getPrice();
				
				odDao.insert(od);
			}
			
			double newBonus= member.getBonus() - om.getBonusUse() + om.getTotalPrice() * 0.05; // 紅利點數取得公式?
			member.setBonus(newBonus);
			mbDao.update(member);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 配合其他service方法使用，將OrderMaster list 轉為 ManageOrder list
	@Override
	public List<ManageOrder> fromOrderToManageOrder(List<OrderMaster> omlist) {
		List<ManageOrder> mgOrderList = new ArrayList<>();
		
		for (OrderMaster om : omlist) {
			ManageOrder mgOd = new ManageOrder();
			mgOd.setChecked(false);
			Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			mgOd.setBuyDate(format.format(om.getCommitDate()));
			mgOd.setMemberId(om.getMemberId());
			mgOd.setOrderId(om.getOrderId());
			mgOd.setTotal(om.getTotalPrice());
			
			int judgeOrder = om.getOrderStatus();
			switch (judgeOrder) {
			case 1:
				mgOd.setOrderStatus("已成立");
				break;
			case 2:
				mgOd.setOrderStatus("已取消");
				break;
			case 3:
				mgOd.setOrderStatus("申請取消");
				break;
			case 4:
				mgOd.setOrderStatus("申請退貨");
				break;	
			}
			
			int judgeDeli = om.getDeliverState();
			switch (judgeDeli) {
			case 0:
				mgOd.setDeliStatus("未出貨");;
				break;
			case 1:
				mgOd.setDeliStatus("已出貨");
				break;
			case 2:
				mgOd.setDeliStatus("已到貨");
			}
			
			int judgePay = om.getPayStatus();
			switch (judgePay) {
			case 1:
				mgOd.setPayStatus("待付款");
				break;
			case 2:
				mgOd.setPayStatus("已付款");
				break;
			case 3:
				mgOd.setPayStatus("貨到付款");
				break;
			}
			
			Member member = mbDao.selectById(om.getMemberId());
			mgOd.setBuyer(member.getNick());
			
			mgOrderList.add(mgOd);
		}
		return mgOrderList;
	}

	@Transactional
	@Override
	public List<ManageOrder> showAllMgOrderList(Integer limit, Integer offset) {
		try {
			List<OrderMaster> omList = omdao.selectAllWithLimitAndOffset(limit, offset);
			List<ManageOrder> mgOrderList = fromOrderToManageOrder(omList);
			return mgOrderList;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public List<ManageOrder> showMgOrderListSortedWithLimitOffset
	(Map<String, String> orderByCondition, Map<String, Integer> limitAndOffset) {
		try {
			List<OrderMaster> omList = omdao.selectOrderbyConditionAndLimitOffset(orderByCondition, limitAndOffset);
			List<ManageOrder> mgOrderList = fromOrderToManageOrder(omList);
			return mgOrderList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public List<ManageOrder> ambiguMemberNameSearch
	(String partMemberName, Integer sortWay, Map<String, Integer> limitOffset) {
		try {
			List<Member> candidate = omdao.selectLikeMemberName(partMemberName);
			List<OrderMaster> results = new ArrayList<>();
			for (Member member : candidate) {
				results.addAll(omdao.selectByMemberId(member.getMember_id()));
			}
			
			Integer resultsLength = results.size();
			Integer limit = limitOffset.get("limit");
			Integer offset = limitOffset.get("offset");
			Integer lastIndex = (offset + limit > resultsLength)? resultsLength : offset + limit;
			
			List<OrderMaster> subResults = results.subList(offset, lastIndex);
			if (sortWay == 1) {
				Collections.reverse(subResults);
			}
			
			List<ManageOrder> mgOrderList = fromOrderToManageOrder(subResults);
			return mgOrderList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public Integer ambiguMemberNameSearchLength(String partMemberName) {
		try {
			List<Member> candidate = omdao.selectLikeMemberName(partMemberName);
			List<OrderMaster> results = new ArrayList<>();
			for (Member member : candidate) {
				results.addAll(omdao.selectByMemberId(member.getMember_id()));
			}
			
			Integer resultsLength = results.size();
			return resultsLength;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public long countDataNum(Map<String, Integer> condition) {
		try {
			long resultNum = 0;
			if (condition.isEmpty()) {
				resultNum = omdao.simpleCountDatNum();
			} else {
				resultNum = omdao.countdataNumWithCondition(condition);
			}
			return resultNum;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional
	@Override
	public OrderMaster getOne(Integer orderId) {
		try {
			OrderMaster om = omdao.selectById(orderId);
			return om;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public boolean updateFromManager(OrderMaster fromManager) {
		try {
			omdao.update(fromManager);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean updateFromMember(OrderMaster fromMember) {
		try {
			OrderMaster oldone = omdao.selectById(fromMember.getOrderId());
			oldone.setOrderStatus(fromMember.getOrderStatus());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public List<MemberViewOrder> memberOrderList(Map<String, Integer> whereCondition ,Map<String, Integer> limitAndOffset) {
		try {
			List<MemberViewOrder> mvList = new ArrayList<>();
			
			List<OrderMaster> omlist = omdao.selectWithConditionAndLimitOffset(whereCondition, limitAndOffset);
			for (OrderMaster om : omlist) {
				MemberViewOrder mvod = new MemberViewOrder();
				mvod.setOrderMaster(om);
				if (om.getCouponId() != null) {
					mvod.setCoupon(cpDao.selectById(om.getCouponId()));
				}
				
				mvod.setGetBonus((int)(om.getTotalPrice() * 0.05));
				List<TransOrderProduct> trProdcuts = new ArrayList<>();
				List<OrderDetail> orderDetails = odDao.selectByOrderId(om.getOrderId());
				for (OrderDetail od : orderDetails) {
					Product pd = pdDao.selectById(od.getPkOrderDeatail().getProductID());
					TransOrderProduct trPd = new TransOrderProduct();
					
					trPd.setBrand(pd.getBrand());
					trPd.setBuyAmount(od.getQuantity());
					trPd.setChecked(true);
					trPd.setPrice(pd.getPrice());
					trPd.setProductId(od.getPkOrderDeatail().getProductID());
					trPd.setProductName(pd.getProductName());
					if (pd.getProductImages().isEmpty()) {
						trPd.setProductImgUrl(null);
					} else {
						trPd.setProductImgUrl(productService.getProductIndexImg(pd.getId()).getImage());
					}
					trPd.setStockAmount(pd.getAmount());
					
					trProdcuts.add(trPd);
				}
				mvod.setTrList(trProdcuts);				
				mvList.add(mvod);
			}
			return mvList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public TransOrderProduct getOneProduct(Integer productId) {
		try {
			Product pd = pdDao.selectById(productId);
			if (pd == null) {
				return null;
			}
			TransOrderProduct trpd = new TransOrderProduct();
			trpd.setBrand(pd.getBrand());
			trpd.setBuyAmount(1);
			trpd.setPrice(pd.getPrice());
			trpd.setProductId(pd.getId());
		
			if (pd.getProductImages().isEmpty()) {
				trpd.setProductImgUrl(null);
			} else {
				trpd.setProductImgUrl(productService.getProductIndexImg(pd.getId()).getImage());
			}
			trpd.setProductName(pd.getProductName());
			trpd.setStockAmount(pd.getAmount());
			return trpd;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public List<TransOrderProduct> getRecomendFromAll(Integer recomendAmount) {
		try {
			List<TransOrderProduct> trPdList = new ArrayList<>();
			
			List<Product> pdList = pdDao.selectByBuyTimes(recomendAmount, "");
			for (Product pd : pdList) {
				TransOrderProduct trPd = new TransOrderProduct();
				trPd.setPrice(pd.getPrice());
				trPd.setProductId(pd.getId());
				if (!pd.getProductImages().isEmpty()) {
					trPd.setProductImgUrl(productService.getProductIndexImg(pd.getId()).getImage());
				}
				trPd.setProductName(pd.getProductName());
				
				trPdList.add(trPd);
			}
	
			return trPdList;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	@Override
	public List<ManageOrder> getJedisOrderMasterResults(OrderSelection selectionCode, Integer sortWay, Integer offset) {
		try {
			String checkTime = jdOmDao.getResults("Order:SaveTime");
			String retrieve = jdOmDao.getResults("Order:" + selectionCode.getCode());

			if (checkTime == null || retrieve == null) {
				for (int i = 1; i < OrderSelection.values().length; i++ ) {
					saveOrderMasterResults(OrderSelection.values()[i]);
				}
				checkTime = jdOmDao.getResults("Order:SaveTime");
			}
			
			Long saveTimeValue = Long.valueOf(checkTime);
			Long nowTime = new Date().getTime();
			
			if (nowTime - saveTimeValue > 30 * 60 * 1000) {
				for (int i = 1; i < OrderSelection.values().length; i++ ) {
					saveOrderMasterResults(OrderSelection.values()[i]);
				}
				retrieve = jdOmDao.getResults("Order:" + selectionCode.getCode());
			} 

			List<OrderMaster> jedisContent = gson.fromJson(retrieve, new TypeToken<List<OrderMaster>>(){});
			
			
			if (sortWay == 1) {
				Collections.reverse(jedisContent);
			}
			
			int limit = 10;
			int jedisContentSize = jedisContent.size();
			
			List<OrderMaster> omList = jedisContent.subList(offset, (offset + limit > jedisContentSize)? jedisContentSize : offset + limit);
			List<ManageOrder> mgOrderList = fromOrderToManageOrder(omList);
			return mgOrderList;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	class PayStatusComparator implements Comparator<OrderMaster> {

		@Override
		public int compare(OrderMaster o1, OrderMaster o2) {
			if (o1.getPayStatus() > o2.getPayStatus()) {
				return 1;
			} else if (o1.getPayStatus() < o2.getPayStatus()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	class DeliverStateComparator implements Comparator<OrderMaster> {

		@Override
		public int compare(OrderMaster o1, OrderMaster o2) {
			if (o1.getDeliverState() > o2.getDeliverState()) {
				return 1;
			} else if (o1.getDeliverState() < o2.getDeliverState()) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}
	
	class OrderStatusComparator implements Comparator<OrderMaster>{

		@Override
		public int compare(OrderMaster o1, OrderMaster o2) {
			if (o1.getOrderStatus() > o2.getOrderStatus()) {
				return 1;
			} else if (o1.getOrderStatus() < o2.getOrderStatus()) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}

	@Override  //須搭配其他服務使用
	public boolean saveOrderMasterResults(OrderSelection selectCode) {
		Long now = new Date().getTime();
		jdOmDao.saveOrderMasterResults("Order:SaveTime", now.toString());
		
		PayStatusComparator pscom = new PayStatusComparator();
		DeliverStateComparator dlcom = new DeliverStateComparator();
		OrderStatusComparator odcom = new OrderStatusComparator();
		
		List<OrderMaster> newResults = new ArrayList<>();
		
		switch (selectCode) {
		case PAID:
			newResults = omdao.selectByPaystatus(2);
			Collections.sort(newResults, pscom);
			break;
		case UNPAID:
			newResults = omdao.selectByPaystatus(1);
			Collections.sort(newResults, pscom);
			break;
		case PAIDONEDELI:
			newResults = omdao.selectByPaystatus(3);
			Collections.sort(newResults, pscom);
			break;
		case DELIVERD:
			newResults = omdao.selectByDeliverState(1);
			Collections.sort(newResults, dlcom);
			break;
		case UNDELI:
			newResults = omdao.selectByDeliverState(0);
			Collections.sort(newResults, dlcom);
			break;
		case DONE:
			newResults = omdao.selectByOrderStatus(1);
			Collections.sort(newResults, odcom);
			break;
		case CANCELED:
			newResults = omdao.selectByOrderStatus(2);
			Collections.sort(newResults, odcom);
			break;
		case APPLYCAN:
			newResults = omdao.selectByOrderStatus(3);
			Collections.sort(newResults, odcom);
			break;
		case APPLYRETURN:
			newResults = omdao.selectByOrderStatus(4);
			Collections.sort(newResults, odcom);
			break;
		}
		
		String key = "Order:" + selectCode.getCode();
		String content = gson.toJson(newResults);
		jdOmDao.saveOrderMasterResults(key, content);
		return true;
	}

	@Transactional
	@Override
	public boolean renewOrderMasterResults() {
		try {
			for (int i = 1; i < OrderSelection.values().length; i++ ) {
				saveOrderMasterResults(OrderSelection.values()[i]);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
