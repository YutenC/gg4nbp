package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.pojo.ErrorMsg;
import gg.nbp.web.shop.shopproduct.pojo.RequestMsg;
import gg.nbp.web.shop.shopproduct.service.CouponManagerService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import gg.nbp.web.shop.shopproduct.util.ObjectInstance;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class CouponManagerController {

    @Autowired
    CouponManagerService couponManagerService;

    public CouponManagerController() {
    }

    public String getAllCouponActivity(HttpSession session) {
        System.out.println("getAllCouponActivity");
        List<CouponActivity> couponActivities = null;
        String out = null;
        RequestMsg requestMsg = null;
        ErrorMsg errorMsg = null;

        try {
            couponActivities = couponManagerService.getAllCouponActivity();

            requestMsg = new RequestMsg("success", couponActivities);
            out = ConvertJson.toJson(requestMsg);
        } catch (RuntimeException e) {
            if ("Could not get a resource from the pool".equals(e.getMessage())) {
                System.out.println("Could not get a resource from the pool.........");
            }
            requestMsg = new RequestMsg("error", e.getMessage());
            out = ConvertJson.toJson(requestMsg);

        }


        return out;
    }




    public String addCouponActivity(HttpSession session, String newCouponActivity) {
        System.out.println("addCouponActivity");

        Gson gson = new Gson();
        CouponActivity couponActivity = gson.fromJson(newCouponActivity, CouponActivity.class);

        couponManagerService.addCouponActivity(couponActivity);
        return "";
    }

    public String updateCouponActivity(String newCouponActivity) {
        Gson gson = new Gson();
        CouponActivity couponActivity = gson.fromJson(newCouponActivity, CouponActivity.class);
        couponManagerService.updateCouponActivity(couponActivity);
        return "";
    }


    public void autoGenerateCouponActivity() {
        couponManagerService.generateCouponActivity();
    }


    public String deleteCoupon(Integer couponId) {
        System.out.println("deleteCoupon");
        couponManagerService.deleteCoupon(couponId);
        return "";
    }


}
