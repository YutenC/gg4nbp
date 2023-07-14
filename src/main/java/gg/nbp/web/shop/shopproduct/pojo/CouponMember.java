package gg.nbp.web.shop.shopproduct.pojo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponMember {

    private Integer member_id;

    private String account;

    private String email;

    private Date sendEmailTime;

    private boolean check;

    private String discountCode;


    public CouponMember(Integer member_id,String account, String email) {
        this.member_id=member_id;
        this.account = account;
        this.email = email;
    }
}
