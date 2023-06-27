package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.servlet.http.HttpSession;

@Component
public class CouponController {

    @Autowired
    CouponService couponService;

    public CouponController() {
    }


    public String getCoupon(HttpSession session) {
        System.out.println("getCoupon");

        Gson gson = new Gson();

        String json_str = gson.toJson(couponService.getCouponById(1));

        return json_str;
    }

    public String getCouponByDiscountCode(String discountCode) {
        Coupon coupon = couponService.getCouponByDiscountCode(discountCode);
        Gson gson = new Gson();
        return gson.toJson(coupon);
    }

    public String getCouponByActivity(HttpSession session, String activityCode) {
        System.out.println("getCouponByActivity");

        Gson gson = new Gson();
        String json_str = gson.toJson(couponService.getCouponById(1));

        return json_str;
    }
}
