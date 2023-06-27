package gg.nbp.web.shop.shopproduct.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CouponActivity {
    String activityName;
    String activityCode;//唯一
    Integer couponId;
    Coupon coupon;

    public CouponActivity() {
    }

    public CouponActivity(String activityName, String activityCode) {
        this.activityName = activityName;
        this.activityCode = activityCode;
    }

    @Override
    public String toString() {
        return "CouponActivity{" +
                "activityName='" + activityName + '\'' +
                ", activityCode='" + activityCode + '\'' +
                ", couponId=" + couponId +
                ", coupon=" + coupon +
                '}';
    }
}
