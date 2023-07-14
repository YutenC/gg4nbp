package gg.nbp.web.shop.shopproduct.controller;

import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CouponController {

    @Autowired
    CouponService couponService;

    public CouponController() {
    }

    public String getCouponByDiscountCode(String discountCode) {
        Coupon coupon = couponService.getCouponByDiscountCode(discountCode);
        return ConvertJson.toJson(coupon);
    }


}
