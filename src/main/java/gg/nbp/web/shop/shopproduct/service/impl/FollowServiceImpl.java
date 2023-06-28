package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.dao.FollowDao;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.FollowListId;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.pojo.ResFollowList;
import gg.nbp.web.shop.shopproduct.service.FollowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowDao followDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImageDao productImageDao;

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
    public List<ResFollowList> getFollowByMember(Member member) {
        List<FollowList> followLists=  followDao.selectByMemberId(member.getMember_id());
        List<ResFollowList> resFollowLists=new ArrayList<>();


        for(int i=0;i<followLists.size();i++){
            Product product=productDao.selectById(followLists.get(i).getId().getProductId());
            ProductImage productImage=productImageDao.getIndexImgByProductId(followLists.get(i).getId().getProductId());
            resFollowLists.add(new ResFollowList(product.getId(),product.getProductName(),product.getPrice(),product.getAmount(),productImage.getImage()));
        }

        return resFollowLists;
    }

    @Override
    public List<FollowList> getFollowByMemberId(Integer memberId) {
        return followDao.selectByMemberId(memberId);
    }
}
