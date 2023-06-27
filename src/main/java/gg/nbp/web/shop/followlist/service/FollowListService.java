package gg.nbp.web.shop.followlist.service;

import java.util.List;

import gg.nbp.core.service.CoreService;
import gg.nbp.web.shop.followlist.util.ResFollowList;

public interface FollowListService extends CoreService{
	
	List<ResFollowList> getAllFollowProduct(Integer memberId);
	
	boolean deleteFollowList(Integer memberId, Integer productId);
	
	boolean addFollowList(Integer memberId, Integer productId);
}
