package gg.nbp.web.SecondHand.sale.entity;

import java.time.LocalDate;

import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.Member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "secondhand_order", schema = "five")
public class SecondhandOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Product_id", nullable = false)
    private SecondhandProduct product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Member_id", nullable = false)
    private Member member;

    @Column(name = "Order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Manager_id")
    private Manager manager;

    @Column(name = "Deliver_state", nullable = false)
    private Byte deliverState;

    @Column(name = "Pay_state", nullable = false)
    private Byte payState;

    @Column(name = "Deliver_id", length = 30)
    private String deliverId;

    @Column(name = "Deliver_location", nullable = false, length = 50)
    private String deliverLocation;

    @Column(name = "Receive", nullable = false)
    private Byte receive;

    @Column(name = "Deliver_fee", nullable = false)
    private Integer deliverFee;

    @Column(name = "Total_price", nullable = false)
    private Integer totalPrice;

}