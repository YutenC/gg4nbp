package gg.nbp.web.shop.shopproduct.pojo;

public class RequestMsg {

    private String state;
    private String msg;
    private Object content;

    private RequestMsg(RequestMsg.Builder builder) {
        state = builder.state;
        msg = builder.msg;
        content = builder.content;
    }

    public RequestMsg(String state, Object content) {
        this.state = state;
        this.content = content;
    }

    public RequestMsg(String state, String msg, Object content) {
        this.state = state;
        this.msg = msg;
        this.content = content;
    }

    public static class Builder {
        private String state="";
        private String msg="";
        private Object content="";


        public RequestMsg.Builder setState(String state) {
            this.state = state;
            return this;
        }

        public RequestMsg.Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public RequestMsg.Builder setContent(Object content) {
            this.content = content;
            return this;
        }

        public RequestMsg build() {
            return new RequestMsg(this);
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
