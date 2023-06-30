package gg.nbp.web.shop.shopproduct.pojo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponMember {


    private String account;

    private String email;

    private Date sendEmailTime;

    private boolean check;

//    private String dis
//    private java.sql.Date birth;


    public CouponMember(String account, String email) {
        this.account = account;
        this.email = email;
    }
}
