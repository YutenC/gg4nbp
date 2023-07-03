package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.pojo.CouponMember;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;
import gg.nbp.web.shop.shopproduct.pojo.ErrorMsg;
import gg.nbp.web.shop.shopproduct.pojo.ResponseMsg;
import gg.nbp.web.shop.shopproduct.service.CouponManagerService;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.ConvertJson;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class CouponManagerController {

    @Autowired
    CouponManagerService couponManagerService;

    @Autowired
    CouponService couponService;

    public CouponManagerController() {
    }

    public String getAllCouponActivity(HttpSession session) {
//        System.out.println("getAllCouponActivity");
        List<CouponActivity> couponActivities = null;
        String out = null;
        ResponseMsg requestMsg = null;
        ErrorMsg errorMsg = null;

        try {
            couponActivities = couponManagerService.getAllCouponActivity();

            requestMsg = new ResponseMsg("ok", couponActivities);
            out = ConvertJson.toJson(requestMsg);
        } catch (RuntimeException e) {
            if ("Could not get a resource from the pool".equals(e.getMessage())) {
                System.out.println("Could not get a resource from the pool.........");
            }
            requestMsg = new ResponseMsg("error", e.getMessage());
            out = ConvertJson.toJson(requestMsg);

        }


        return out;
    }

    public String getCouponActivityByCondition(DaoConditionSelect daoConditionSelect){
        List<CouponActivity>  couponActivities=  couponManagerService.getCouponActivityByCondition(daoConditionSelect);

        ResponseMsg responseMsg=new ResponseMsg("ok",couponActivities);
        return ConvertJson.toJson(responseMsg);
    }

    public String addCouponActivity(HttpSession session, String newCouponActivity) {
//        System.out.println("addCouponActivity");

        Gson gson = new Gson();
        CouponActivity couponActivity = gson.fromJson(newCouponActivity, CouponActivity.class);

        couponManagerService.addCouponActivity(couponActivity);
        return "";
    }

    @Transactional
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
//        System.out.println("deleteCoupon");
        couponManagerService.deleteCoupon(couponId);
        return "";
    }


    public String getCouponMemberInfo(){
        List<CouponMember> couponMembers= couponManagerService.getCouponMemberInfo();
        return ConvertJson.toJson(couponMembers);
    }

    public String sendEmail(int action, List<CouponMember> couponMembers) {
        ResponseMsg responseMsg= couponManagerService.sendEmail(action,couponMembers);
        return ConvertJson.toJson(responseMsg);
    }

//    @Transactional
//@Transactional(propagation = Propagation.NESTED)
    public String publishCouponActivity(Integer couponId) {
        couponManagerService.publishCouponActivity(couponId);
        ResponseMsg responseMsg=new ResponseMsg.Builder().setState("ok").build();
        return ConvertJson.toJson(responseMsg);
    }



}
