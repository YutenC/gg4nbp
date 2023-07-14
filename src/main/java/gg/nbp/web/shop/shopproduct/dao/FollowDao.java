package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.shop.shopproduct.core.CoreDao;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;
import java.util.List;


public interface FollowDao  extends CoreDao<FollowList, FollowListId> {
    List<FollowList> selectByMemberId(Integer memberId);
}
