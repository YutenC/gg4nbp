package gg.nbp.web.shop.shopproduct.util;

public enum CouponState {
    //狀態 {0：新增/未發佈  ; 1:有效/已發佈 ; 2 :失效; }

    unPublish(0),
    publish(1),
    inValid(2);
    int value;

    CouponState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
