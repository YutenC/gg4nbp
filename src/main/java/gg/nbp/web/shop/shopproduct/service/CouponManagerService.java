package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.CouponActivity;

import java.util.List;

public interface CouponManagerService {
    void generateCouponActivity();
    void addCouponActivity(CouponActivity couponActivity);

    List<CouponActivity> getAllCouponActivity();
    List<CouponActivity> searchCouponActivity();

    void updateCouponActivity(CouponActivity couponActivity);


    boolean deleteCoupon(Integer couponId);

}
