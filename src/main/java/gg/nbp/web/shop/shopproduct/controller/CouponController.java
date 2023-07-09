package gg.nbp.web.shop.shopproduct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import jakarta.servlet.http.HttpSession;



@Component
public class CouponController {

    @Autowired
    CouponService couponService;

    public CouponController() {
    }

    public String getCoupon(HttpSession session) {
        System.out.println("getCoupon");

        return ConvertJson.toJson(couponService.getCouponById(1));
    }

    public String getCouponByDiscountCode(String discountCode) {
        Coupon coupon = couponService.getCouponByDiscountCode(discountCode);
        return ConvertJson.toJson(coupon);
    }

    public String getCouponByActivity(HttpSession session, String activityCode) {
        System.out.println("getCouponByActivity");


        return ConvertJson.toJson(couponService.getCouponById(1));
    }
}
