package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.Coupon;


public interface CouponService {

    Coupon getCouponById(Integer id);
    Coupon getCouponByDiscountCode(String  discountCode);

    int addCoupon(Coupon coupon);
    boolean deleteCoupon(Integer id);
}
