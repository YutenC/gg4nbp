package gg.nbp.web.shop.shopproduct.redisdao;

import gg.nbp.web.shop.shopproduct.entity.CouponActivity;

import java.util.List;

public interface CouponActivityRedisDao {

    List<CouponActivity> getAllCouponActivity();
    List<String> getAllCouponActivity_string();
    void addCouponActivity(CouponActivity couponActivity);
    void  updateCouponActivity(CouponActivity couponActivity);
    void deleteCouponActivity(Integer couponId);


}
