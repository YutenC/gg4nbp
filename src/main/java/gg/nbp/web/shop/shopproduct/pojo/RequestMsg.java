package gg.nbp.web.shop.shopproduct.pojo;

public class RequestMsg {

    private String state;

    private String msg;
    private Object content;

    public RequestMsg(String state, Object content) {
        this.state = state;
        this.content = content;
    }

    public RequestMsg(String state, String msg, Object content) {
        this.state = state;
        this.msg = msg;
        this.content = content;
    }
}
