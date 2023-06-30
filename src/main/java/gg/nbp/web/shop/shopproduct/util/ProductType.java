package gg.nbp.web.shop.shopproduct.util;

public enum ProductType {

//商品種類 { 0: NS主機; 1: NS手把; 2: NS卡帶; 3: NS周邊;
// 10: XBOX主機; 11: XBOX手把; 12: XBOX遊戲片; 13: XBOX周邊;
// 20: PS主機; 21: PS手把; 22: PS遊戲片; 23: PS周邊 }

    NSMachine(0),
    NSHandle(1),
    NSGame(2),
    NSAround(3),

    XBOXMachine(10),
    XBOXHandle(11),
    XBOXGame(12),
    XBOXAround(13),

    PSMachine(20),
    PSHandle(21),
    PSGame(22),
    PSAround(23);

    private int value;

    ProductType(int value){
        this.value=value;
    }


    public int getValue() {
        return value;
    }



}
