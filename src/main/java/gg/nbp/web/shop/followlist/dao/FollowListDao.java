package gg.nbp.web.shop.followlist.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.shop.followlist.entity.FollowList;

public interface FollowListDao extends CoreDao<FollowList, Integer>{
	
	List<FollowList> selectByMemeberId(Integer memberId);
	
	boolean deleteByCompositePK(FollowList flist);
}
