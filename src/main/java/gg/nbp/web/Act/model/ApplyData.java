package gg.nbp.web.Act.model;



public class ApplyData {

    private String name;
    private String email;
    private String message;

    // getter 和 setter...

    @Override
    public String toString() {
        return String.format("Name: %s, Email: %s, Message: %s", name, email, message);
    }
    public String getMessage() {
        return this.message;
    }
    public String getEmail() {
        return this.email;
    }


}

