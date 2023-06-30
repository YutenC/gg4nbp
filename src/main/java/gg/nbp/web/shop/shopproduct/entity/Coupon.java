package gg.nbp.web.shop.shopproduct.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter

public class Coupon {
    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer discount;

    @Column(name = "condition_price")
    Integer conditionPrice;
    java.util.Date deadline;

    @Column(name = "discount_code")
    String discountCode;

    @Column(name = "state")
    Integer state;

    public Coupon() {
    }

    public Coupon(Integer discount, Integer conditionPrice, Date deadline, String discountCode,Integer state) {
        this.discount = discount;
        this.conditionPrice = conditionPrice;
        this.deadline = deadline;
        this.discountCode = discountCode;
        this.state=state;
    }
}
