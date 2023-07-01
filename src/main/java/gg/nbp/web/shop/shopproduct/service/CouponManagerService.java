package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.pojo.CouponMember;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;
import gg.nbp.web.shop.shopproduct.pojo.RequestMsg;
import gg.nbp.web.shop.shopproduct.pojo.ResponseMsg;

import java.util.List;

public interface CouponManagerService {
    void generateCouponActivity();
    void addCouponActivity(CouponActivity couponActivity);

    List<CouponActivity> getAllCouponActivity();

    List<CouponActivity> getCouponActivityByCondition(DaoConditionSelect daoConditionSelect) throws RuntimeException;

    CouponActivity getCouponActivityByCouponId(Integer couponId);

    List<CouponActivity> searchCouponActivity();

    void updateCouponActivity(CouponActivity couponActivity);


    boolean deleteCoupon(Integer couponId);


    List<CouponMember>  getCouponMemberInfo();

    void publishCouponActivity(Integer couponId);
    ResponseMsg sendEmail(int action, List<CouponMember> couponMembers);
}
