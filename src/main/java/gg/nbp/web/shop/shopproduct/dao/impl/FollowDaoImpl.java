package gg.nbp.web.shop.shopproduct.dao.impl;

import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.FollowDao;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;
import org.springframework.stereotype.Repository;

@Repository
public class FollowDaoImpl extends CoreDaoImpl<FollowList, FollowListId> implements FollowDao {
}
