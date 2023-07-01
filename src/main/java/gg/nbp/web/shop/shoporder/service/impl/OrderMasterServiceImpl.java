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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import gg.nbp.web.Member.dao.MemberDao;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.shop.shoporder.dao.JedisOrderMasterDao;
import gg.nbp.web.shop.shoporder.dao.OrderDetailDao;
import gg.nbp.web.shop.shoporder.dao.OrderMasterDao;
import gg.nbp.web.shop.shoporder.entity.OrderDetail;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.entity.PKOrderDetail;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shoporder.service.OrderDetailService;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.service.ShoppingListService;
import gg.nbp.web.shop.shoporder.util.ManageOrder;
import gg.nbp.web.shop.shoporder.util.MemberViewOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;
import gg.nbp.web.shop.shoporder.util.ResOrderMaster;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;
import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ProductType;
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
	private ProductService pService;
	@Autowired
	private CouponService cService;
	@Autowired
	private ShoppingListService shlistService;
	@Autowired
	private OrderDetailService odService;
	@Autowired
	private MemberService mService;
	
	private Gson gson;
	
	public OrderMasterServiceImpl() {
		this.gson = new Gson();
	}

	@Transactional
	@Override
	public boolean establishNewOrder(OrderMaster om, List<TransOrderProduct> trObjList, Member member) {
		try {
			omdao.insert(om);
			Integer orderId = om.getOrderId();
			List<ShoppingList> spLists = new ArrayList<>();  // 預先準備刪除時使用的購物清單列表
			
			// 新增OrderDetail
			Integer prodcutTotalPrice = 0;
			for (TransOrderProduct trObj : trObjList) {
				PKOrderDetail pkod = new PKOrderDetail();
				pkod.setOrderId(orderId);
				pkod.setProductId(trObj.getProductId());
				
				OrderDetail od = new OrderDetail();
				od.setPkOrderDetail(pkod);
				od.setQuantity(trObj.getBuyAmount());
				Product checkProduct = pService.getProductById(trObj.getProductId());
				od.setTotalPrice(trObj.getBuyAmount() * checkProduct.getPrice());
				odDao.insert(od);
				
				prodcutTotalPrice += trObj.getBuyAmount() * checkProduct.getPrice();

				// 調整商品庫存
				Product pd = pService.getProductById(trObj.getProductId());
				Integer oldStock = pd.getAmount();
				pd.setAmount(oldStock - trObj.getBuyAmount());
				pd.setBuyTimes(pd.getBuyTimes() + trObj.getBuyAmount());
				pdDao.update(pd);
				
				// 刪除購物清單
				PKShoppingList pksplist = new PKShoppingList();
				pksplist.setMemmberId(member.getMember_id());
				pksplist.setProductId(trObj.getProductId());
				
				ShoppingList splist = new ShoppingList();
				splist.setPkShoppingList(pksplist);
				
				spLists.add(splist);
			}

			shlistService.removeItem(spLists);
			
			// 調整會員持有紅利
			double newBonus= member.getBonus() - om.getBonusUse() + om.getTotalPrice() * OrderMasterService.BONUS_RATE; // 紅利點數取得公式?
			member.setBonus(newBonus);
			mbDao.update(member);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public OrderMaster createNewOrderMaster(List<TransOrderProduct> trObjList, JsonObject cardDetail,
			JsonObject addressDetail, Member member, String commitType, String pickType, String discountRadio,
			String couponCode, String bonus) {
		try {
			OrderMaster om = new OrderMaster();
			
			om.setMemberId(member.getMember_id());
			
			java.util.Date date = new java.util.Date();
			om.setCommitDate(new java.sql.Timestamp(date.getTime()));
			
			Integer commitTypeSetting = 0;
			switch (commitType) {
			case "credit":
				commitTypeSetting = 1;
				om.setPayStatus(2);
				break;
			case "transfer":
				commitTypeSetting = 2;
				om.setPayStatus(1);
				break;
			case "onDeliver":
				commitTypeSetting = 3;
				om.setPayStatus(3);
				break;
			}
			om.setCommitType(commitTypeSetting);

			om.setDeliverState(0); // 預設固定為未出貨

			Integer takuhaiFee = 100;
			Integer toCvsFee = 200;
			om.setDeliverFee(pickType.equals("takuhai")? takuhaiFee : toCvsFee);  // 運費

			Integer takuhaiCode = 1;
			Integer toCvsCode = 2;
			om.setPickType(pickType.equals("takuhai")? takuhaiCode : toCvsCode);  // 取貨方式
			
			om.setDeliverLocation(addressDetail.get("county").toString().replace("\"", "") + addressDetail.get("address").toString().replace("\"", ""));

			Integer productPrice = 0;	// 檢核消費是否達優惠券門檻
			for (TransOrderProduct trObj : trObjList) {
				if (trObj.isChecked() == true) {
					TransOrderProduct checkProduct = getOneProduct(trObj.getProductId());
					productPrice += checkProduct.getPrice() * trObj.getBuyAmount();
				}
			}
			
			Coupon checkCoupon = null;
			if (couponCode != null && couponCode.trim().length() > 0) {
				checkCoupon = cService.getCouponByDiscountCode(couponCode);  // 根據優惠卷代碼取得優惠卷物件
			} 
			
			Integer couponDiscount = 0;	// 有取得符合條件的優惠券物件，且消費額度大於門檻
			if (checkCoupon != null && checkCoupon.getConditionPrice() < productPrice) {
				couponDiscount = checkCoupon.getDiscount();
			}
			
			Integer usedbonus = 0;	// 確保所輸入的紅利點數未超過會員所持有量
			if (bonus != null && bonus.trim().length() != 0) {
				usedbonus = Integer.valueOf(bonus.trim());
				if (usedbonus < (member.getBonus() == null? 0: member.getBonus())) {
					usedbonus = 0;
				}
			}
			
			Integer totalPrice = 0;
			if (checkCoupon != null && "coupon".equals(discountRadio)) {	// 會員選擇使用優惠卷折抵
				totalPrice = productPrice - couponDiscount;
				om.setCouponId(checkCoupon.getId());
			} else {							// 會員選擇使用紅利折抵
				totalPrice = productPrice - usedbonus;
				om.setBonusUse(usedbonus);
			}
			
			om.setTotalPrice(totalPrice);
			
			om.setOrderStatus(1);  // 預設固定為未付款

			return om;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
			
			if (sortWay == 1) {
				Collections.reverse(results);
			}
			
			List<OrderMaster> subResults = results.subList(offset, lastIndex);

			List<ManageOrder> mgOrderList = fromOrderToManageOrder(subResults);
			return mgOrderList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public long ambiguMemberNameSearchLength(String partMemberName) {
		try {
			List<Member> candidate = omdao.selectLikeMemberName(partMemberName);
			List<OrderMaster> results = new ArrayList<>();
			for (Member member : candidate) {
				results.addAll(omdao.selectByMemberId(member.getMember_id()));
			}
			
			long resultsLength = results.size();
			return resultsLength;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
			if (fromManager.getOrderStatus() == 2) {	// 若接受退訂，修改order detail的退貨狀態為退貨。※商品評價保留不另刪除。
				List<OrderDetail> odlist = odDao.selectByOrderId(fromManager.getOrderId());
				for (OrderDetail od : odlist) {
					od.setIsReturn(1);
					odDao.update(od);
					
					Product pd = pService.getProductById(od.getPkOrderDetail().getProductId());  // 回補商品庫存量
					pd.setAmount(pd.getAmount() + od.getQuantity());
					pdDao.updateProductAmountBuyTimes(pd);
				}
				
				Member mb = mService.selectMember(fromManager.getMemberId());  // 回扣會員持有紅利，暫不考慮回扣時，會員已把持有紅利使用光，而造成負值的情境
				mb.setBonus(mb.getBonus() - fromManager.getTotalPrice() * BONUS_RATE);
				mService.edit(mb);
			}
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
	public List<MemberViewOrder> memberOrderList(Map<String, Integer> whereCondition , Map<String, Integer> limitAndOffset) {
		try {
			List<MemberViewOrder> mvList = new ArrayList<>();
			
			List<OrderMaster> omlist = omdao.selectWithConditionAndLimitOffset(whereCondition, limitAndOffset);
			for (OrderMaster om : omlist) {
				MemberViewOrder mvod = new MemberViewOrder();
				mvod.setOrderMaster(om);
				if (om.getCouponId() != null) {
					mvod.setCoupon(cpDao.selectById(om.getCouponId()));
				}
				
				mvod.setGetBonus((int)(om.getTotalPrice() * OrderMasterService.BONUS_RATE));
				List<TransOrderProduct> trProdcuts = odService.getOrderDetailByOrderId(om.getOrderId());
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
			Product pd = pService.getProductById(productId);
			if (pd == null) {
				return null;
			}
			TransOrderProduct trpd = new TransOrderProduct();
			trpd.setBrand(pd.getBrand());
			trpd.setBuyAmount(1);
			trpd.setPrice(pd.getPrice());
			trpd.setProductId(pd.getId());
		
			if (pService.getProductIndexImg(pd.getId()) == null) {
				trpd.setProductImgUrl(null);
			} else {
				trpd.setProductImgUrl(pService.getProductIndexImg(pd.getId()).getImage());
			}
			trpd.setProductName(pd.getProductName());
			trpd.setStockAmount(pd.getAmount());
			return trpd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public List<TransOrderProduct> getRecomendFromAll(Integer recomendAmount) {
		try {
			List<TransOrderProduct> trPdList = new ArrayList<>();
			
			List<Product> pdList = pdDao.selectByBuyTimes(recomendAmount, ProductType.any.getValue());
			for (Product pd : pdList) {
				TransOrderProduct trPd = new TransOrderProduct();
				trPd.setPrice(pd.getPrice());
				trPd.setProductId(pd.getId());
				if (pService.getProductIndexImg(pd.getId()) != null) {
					trPd.setProductImgUrl(pService.getProductIndexImg(pd.getId()).getImage());
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

	@Transactional
	@Override
	public ResOrderMaster getOrderResult(OrderMaster om) {
		try {
			ResOrderMaster rsom = new ResOrderMaster();
			
			rsom.setOrderId(om.getOrderId());
			
			rsom.setGetBonus((int) (om.getTotalPrice() * OrderMasterService.BONUS_RATE));
			
			List<TransOrderProduct> odProducts = odService.getOrderDetailByOrderId(om.getOrderId());
			rsom.setOdProducts(odProducts);
			
			Coupon coupon = null;
			if (om.getCouponId() != null) {
				coupon = cService.getCouponById(om.getCouponId());
				rsom.setCheckCoupon(coupon);
			}

			rsom.setUsedBonus(om.getBonusUse());
			rsom.setNowBonus(mService.selectMember(om.getMemberId()).getBonus());
			rsom.setAddress(om.getDeliverLocation());
			
			return rsom;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
