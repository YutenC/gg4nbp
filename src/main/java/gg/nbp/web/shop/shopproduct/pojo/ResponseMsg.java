package gg.nbp.web.shop.shopproduct.pojo;

public class ResponseMsg {

    private String state; //更正為:ok   舊的:success
    private String msg;
    private Object content;

    private ResponseMsg(ResponseMsg.Builder builder) {
        state = builder.state;
        msg = builder.msg;
        content = builder.content;
    }

    public ResponseMsg(String state, Object content) {
        this.state = state;
        this.content = content;
    }

    public ResponseMsg(String state, String msg, Object content) {
        this.state = state;
        this.msg = msg;
        this.content = content;
    }

    public static class Builder {
        private String state="";
        private String msg="";
        private Object content="";


        public  ResponseMsg.Builder setState(String state) {
            this.state = state;
            return this;
        }

        public ResponseMsg.Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResponseMsg.Builder setContent(Object content) {
            this.content = content;
            return this;
        }

        public ResponseMsg build() {
            return new ResponseMsg(this);
        }
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
