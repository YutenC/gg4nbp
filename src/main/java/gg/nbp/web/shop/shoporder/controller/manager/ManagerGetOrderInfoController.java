package gg.nbp.web.shop.shoporder.controller.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gg.nbp.web.Member.service.MemberService;
import gg.nbp.web.Member.service.NoticeService;
import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import gg.nbp.web.shop.shoporder.service.OrderMasterService;
import gg.nbp.web.shop.shoporder.util.ManageOrder;
import gg.nbp.web.shop.shoporder.util.OrderSelection;

@RestController
@RequestMapping("/OrderForManager")
public class ManagerGetOrderInfoController {
	private static final long serialVersionUID = 1L;
    
	@Autowired
	private OrderMasterService orderMasterService;
	
	@GetMapping("/getAll")
	public List<ManageOrder> manageAll(@RequestParam Integer offset, @RequestParam Integer sortBy, @RequestParam Integer sortWay) {
    		int limit = 10;
    		
    		Map<String, String> orderBy = new HashMap<>();
    		switch (sortBy) {
			case 1:
				orderBy.put("orderBy", "orderId");
				break;
			case 2:
				orderBy.put("orderBy", "memberId");
				break;
			case 3:
				orderBy.put("orderBy", "commitDate");
				break;
			case 4:
				orderBy.put("orderBy", "totalPrice");
				break;
			case 5:
				orderBy.put("orderBy", "orderStatus");
				break;
			case 6:
				orderBy.put("orderBy", "payStatus");
				break;
			case 7:
				orderBy.put("orderBy", "deliverState");
				break;
			}

    		switch (sortWay) {
			case 0:
				orderBy.put("orderWay", "ASC");
				break;
			case 1:
				orderBy.put("orderWay", "DESC");
				break;
			}
    		
    		List<ManageOrder> mgOrderList = null;
    		switch (sortBy) {
			case 0:
				mgOrderList = orderMasterService.showAllMgOrderList(limit, offset * limit);
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Map<String, Integer> limitAndOffset = new HashMap<>();
				limitAndOffset.put("limit", limit);
				limitAndOffset.put("offset", offset * limit);
				mgOrderList = orderMasterService.showMgOrderListSortedWithLimitOffset(orderBy, limitAndOffset);
				break;
			}
    		return mgOrderList;
    	}
    	
		@GetMapping("/getAllWithCondition")
		public List<ManageOrder> manageWithCondition(@RequestParam Integer offset, @RequestParam Integer selection, @RequestParam Integer sortWay) {
			int limit = 10;
    		OrderSelection os = OrderSelection.values()[selection - 1];
    		List<ManageOrder> results = orderMasterService.getJedisOrderMasterResults(os, sortWay, offset * limit);
    		return results;
    	}
    	
		@GetMapping("/fresh")
		public void freshList() {
    		orderMasterService.renewOrderMasterResults();
    	}
		
		@GetMapping("/countLength")
		public long countListLength(@RequestParam Integer criteria) {
			
    		Map<String, Integer> condition = new HashMap<>();
    		OrderSelection os = OrderSelection.values()[criteria - 1];
    		switch (os) {
			case ALL:
				break;
			case PAID:
				condition.put("payStatus", 2);
				break;
			case UNPAID:
				condition.put("payStatus", 1);
				break;
			case PAIDONEDELI:
				condition.put("payStatus", 3);
				break;
			case DELIVERD:
				condition.put("deliverState", 1);
				break;
			case UNDELI:
				condition.put("deliverState", 0);
				break;
			case ARRIVED:
				condition.put("deliverState", 2);
				break;
			case DONE:
				condition.put("orderStatus", 1);
				break;
			case CANCELED:
				condition.put("orderStatus", 2);
				break;
			case APPLYCAN:
				condition.put("orderStatus", 3);
				break;
			case APPLYRETURN:
				condition.put("orderStatus", 4);
				break;
			}

    		return orderMasterService.countDataNum(condition);
		}
    	
		@GetMapping("/getOne")
		public OrderMaster getOneOrder(@RequestParam Integer orderId) {
			return orderMasterService.getOne(orderId);
		}
    	
		@GetMapping("/search")
		public List<ManageOrder> searchUser(@RequestParam String searchUser, @RequestParam Integer offset, @RequestParam Integer sortWay) {
    		int limit = 10;
    		Map<String, Integer> limitOffset = new HashMap<>();
    		limitOffset.put("limit", 10);
    		limitOffset.put("offset", offset * limit);
    		
    		return orderMasterService.ambiguMemberNameSearch(searchUser, sortWay, limitOffset);
    	}
    	
		@GetMapping("/searchLength")
		public long searchLength(@RequestParam String keyword) {
    		return orderMasterService.ambiguMemberNameSearchLength(keyword);
    	}
		
}
