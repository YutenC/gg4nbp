package gg.nbp.web.shop.shoporder.dao;

import java.util.List;
import java.util.Map;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;

public interface OrderMasterDao extends CoreDao<OrderMaster, Integer> {

	List<OrderMaster> selectByMemberId(Integer memberId); //找誰買
	
	List<OrderMaster> selectByCommitDate(java.sql.Date lower, java.sql.Date upper); // 找特定日期區間訂單
	
	OrderMaster selectByDeliverNumber(String deliverNumber);
	
	List<OrderMaster> selectByCommitType(Integer commitType); // 找付款方式
	
	List<OrderMaster> selectByDeliverState(Integer deliverState); // 找出貨狀態

	List<OrderMaster> selectByDeliverLocation(String location); // 模糊比對出貨地址
	
	List<OrderMaster> selectByPickType(Integer pickType); // 找取貨方式
	
	List<OrderMaster> selectByCouponId(Integer couponId); // 找哪張單用過特定的優惠券
	
	List<OrderMaster> selectByOrderStatus(Integer orderstatus);
	
	List<OrderMaster> selectByPaystatus(Integer payStatus);
	
	List<OrderMaster> selectAllWithLimitAndOffset(Integer limit, Integer offset);
	
	long simpleCountDatNum();
	
	/**
	 * 
	 * @param condition(Map<String, Integer>): sql where key=value
	 * @return long: how many raws
	 */
	long countdataNumWithCondition(Map<String, Integer> condition);
	
	/**
	 * 
	 * @param memberId
	 * @param whereCondition(Map<String, Integer>) sql where conditon:value concatenate by and 
	 * @param limitAndOffset(Map<String, Integer>) limit:value, offset:value
	 * @return
	 */
	List<OrderMaster> selectWithConditionAndLimitOffset(Map<String, Integer> whereCondition, Map<String, Integer> limitAndOffset);

	/**
	 * 
	 * @param orderbyAndDescOrAsc(Map<String, String>): orderby: value, DescOrAsc: value
	 * @param limitAndOffset(Map<String, Integer>): limit: value, offset: value 
	 * @return
	 */
	List<OrderMaster> selectOrderbyConditionAndLimitOffset(Map<String, String> orderbyAndDescOrAsc, Map<String, Integer> limitAndOffset);

	// 暫時撰寫的內容：模糊比對會員名稱
	List<Member> selectLikeMemberName(String keyword);
}
