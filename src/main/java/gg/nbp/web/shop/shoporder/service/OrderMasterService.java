package gg.nbp.web.shop.shoporder.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.util.ManageOrder;
import gg.nbp.web.shop.shoporder.util.MemberViewOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;
import gg.nbp.web.shop.shoporder.util.ResOrderMaster;
import gg.nbp.web.shop.shoporder.util.TransOrderProduct;

public interface OrderMasterService extends CoreService{
	
	public static final double BONUS_RATE = 0.02;
	
	boolean establishNewOrder(OrderMaster om, List<TransOrderProduct> trObjList, Member member);
	
	List<ManageOrder> showAllMgOrderList(Integer limit, Integer offset);
	
	/**
	 * 
	 * @param orderByCondition(Map<String, String>): order by column and desc or not
	 * @param limitAndOffset(Map<String, Integer>): limt: value, offset: value
	 * @return
	 */
	List<ManageOrder> showMgOrderListSortedWithLimitOffset(Map<String, String> orderByCondition, Map<String, Integer> limitAndOffset);
	
	List<ManageOrder> getJedisOrderMasterResults(OrderSelection selectionCode, Integer sortWay, Integer offset);

	List<ManageOrder> fromOrderToManageOrder(List<OrderMaster> omlist);
	
	boolean saveOrderMasterResults(OrderSelection selectCode);

	boolean renewOrderMasterResults();
	
	List<ManageOrder> ambiguMemberNameSearch(String partMemberName, Integer sortWay, Map<String, Integer> limitOffset);
	
	Integer ambiguMemberNameSearchLength(String partMemberName);
	
	/**
	 * 
	 * @param condition(Map<String, Integer>): sql where key=value
	 * @return long: how many raws
	 */
	long countDataNum(Map<String, Integer> condition);
	
	OrderMaster getOne(Integer orderId);

	OrderMaster createNewOrderMaster(List<TransOrderProduct> trObjList, JsonObject cardDetail, JsonObject addressDetail, Integer memberId,
			String commitType, String pickType, String discountRadio, String couponCode, Integer usedBonus);
	
	boolean updateFromManager(OrderMaster fromManager);
	
	boolean updateFromMember(OrderMaster fromMember);

	/**
	 * 
	 * @param memberId
	 * @param whereCondition(Map<String, Integer>) sql where conditon:value
	 * @param limitAndOffset(Map<String, Integer>) limit:value, offset:value
	 * @return List<MemberViewOrder>
	 */
	List<MemberViewOrder> memberOrderList(Map<String, Integer> whereCondition ,Map<String, Integer> limitAndOffset);
	
	TransOrderProduct getOneProduct(Integer productId);
	
	List<TransOrderProduct> getRecomendFromAll(Integer recomendAmount);
	
	ResOrderMaster getOrderResult(OrderMaster om);

}
