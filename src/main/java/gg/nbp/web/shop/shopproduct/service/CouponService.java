package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupon();
    Coupon getCouponById(Integer id);
    Coupon getCouponByDiscountCode(String  discountCode);

    int addCoupon(Coupon coupon);
    boolean deleteCoupon(Integer id);
}
