package gg.nbp.web.shop.shopproduct.dao.impl;

import gg.nbp.web.shop.shopproduct.core.CoreDaoImpl;
import gg.nbp.web.shop.shopproduct.dao.FollowDao;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowDaoImpl extends CoreDaoImpl<FollowList, FollowListId> implements FollowDao {

    @Override
    public List<FollowList> selectByMemberId(Integer memberId) {
        String hql = "from FollowList  where id.memberId ="+memberId +"order by id.productId asc";
        System.out.println("hql: "+hql);
        return session.createQuery(hql, FollowList.class).getResultList();
    }
}
