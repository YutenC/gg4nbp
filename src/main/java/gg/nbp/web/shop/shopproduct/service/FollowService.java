package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;

import java.util.List;

public interface FollowService {

    FollowList getFollowById(FollowListId followListId);
    FollowListId addFollowById(FollowList followList);
    int addFollow(Integer id, Integer memId);
    int deleteFollowById(FollowListId followListId);
    List<FollowList> getFollowByMemberId(Integer memberId);
}
