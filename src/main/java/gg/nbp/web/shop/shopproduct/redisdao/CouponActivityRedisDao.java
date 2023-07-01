package gg.nbp.web.shop.shopproduct.redisdao;

import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;

import java.util.List;

public interface CouponActivityRedisDao {

    List<CouponActivity> getAllCouponActivity();
    List<CouponActivity> getCouponActivitiesByCoupons(List<Coupon> coupons);
    CouponActivity getCouponActivityByCouponId(Integer couponId);
    void addCouponActivity(CouponActivity couponActivity);
    void  updateCouponActivity(CouponActivity couponActivity);
    void deleteCouponActivity(Integer couponId);


}
