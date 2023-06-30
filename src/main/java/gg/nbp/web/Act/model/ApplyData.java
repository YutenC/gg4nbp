package gg.nbp.web.Act.model;

public class ApplyData {


    private String email;



    // getter 和 setter...

    @Override
    public String toString() {
        return String.format(" Email: %s,",
                email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
