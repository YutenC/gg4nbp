package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shopproduct.dao.FollowDao;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;
import gg.nbp.web.shop.shopproduct.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowDao followDao;

    @Override
    public FollowList getFollowById(FollowListId followListId) {
        return followDao.selectById(followListId);
    }

    @Override
    public FollowListId addFollowById(FollowList followList) {
        return followDao.insert(followList);
    }

    @Override
    public int addFollow(Integer id, Integer memId) {
        FollowListId followListId = new FollowListId(memId, id);

        FollowList followList = getFollowById(followListId);
        if (followList == null) {
            followList = new FollowList(followListId);
            addFollowById(followList);
            return 1;
        } else {
            deleteFollowById(followListId);
            return -1;
        }

    }

    @Override
    public int deleteFollowById(FollowListId followListId) {
        return followDao.deleteById(followListId);
    }

    @Override
    public List<FollowList> getFollowByMemberId(Integer memberId) {
        return followDao.selectByMemberId(memberId);
    }
}
