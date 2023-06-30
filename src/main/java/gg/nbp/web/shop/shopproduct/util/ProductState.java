package gg.nbp.web.shop.shopproduct.util;

public enum ProductState {
    //狀態 {0：新增  ; 1:上架 ; 2 :排定上架; 11 :下架;12 :排定下架}
    NewAdd(0),
    TakeOn(1),
    TakeOning(2),
    TakeOff(11),
    TakeOffing(12);
    private int value;

    ProductState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
