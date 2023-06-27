package gg.nbp.web.shop.shopproduct.controller;

import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.shop.shopproduct.entity.FollowList;
import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.pojo.ResFollowList;
import gg.nbp.web.shop.shopproduct.service.FollowService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowController {

    @Autowired
    FollowService followService;

    public String getFollowByMember(Member member){
        List<ResFollowList>  resFollowLists= followService.getFollowByMember(member);

        return ConvertJson.toJson(resFollowLists);
    }

    public String getFollowByMemberId(Integer memberId) {
        List<FollowList> followLists= followService.getFollowByMemberId(memberId);

        return ConvertJson.toJsonExpose(followLists);
    }

    public Integer addFollow(Integer productId,Integer memId){
        return followService.addFollow(productId,memId);
    }

}
