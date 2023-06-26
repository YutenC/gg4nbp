package gg.nbp.web.SecondHand.sale.entity;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "secondhand_order")
public class SecondhandOrder extends Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id", nullable = false)
    private Integer orderId;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "Product_id", nullable = false)
//    private SecondhandProduct product;
    @JoinColumn(name = "Product_id", nullable = false)
    private Integer productId;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "Member_id", nullable = false)
//    private Member member;
    @JoinColumn(name = "Member_id", nullable = false)
    private Integer memberId;

    @Column(name = "Is_return")
    private Byte isReturn;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Manager_id")
//    private Manager manager;
    @JoinColumn(name = "Manager_id")
    private Integer managerId;

    @Column(name = "Deliver_state", nullable = false)
    private Byte deliverState = 0;

    @Column(name = "Deliver_id", length = 30)
    private String deliverId;

    @Column(name = "Deliver_location", nullable = false, length = 50)
    private String deliverLocation;

    @Column(name = "Receive", nullable = false)
    private Byte receive;

    @Column(name = "Deliver_fee", columnDefinition = "int UNSIGNED")
    private Long deliverFee;

    @Column(name = "Total_price", columnDefinition = "int UNSIGNED not null")
    private Long totalPrice;

    @Column(name = "Pay_state", nullable = false)
    private Byte payState = 0;

    @Column(name = "Order_date", nullable = false)
    @CreationTimestamp
    private Date orderDate;

    @Column(name = "Deliver_name", nullable = false, length = 10)
    private String deliverName;

    @Column(name = "Pay_Bank", length = 20)
    private String payBank;

    @Column(name = "Pay_Number", length = 20)
    private String payNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SecondhandOrder that = (SecondhandOrder) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(memberId, that.memberId) && Objects.equals(isReturn, that.isReturn) && Objects.equals(managerId, that.managerId) && Objects.equals(deliverState, that.deliverState) && Objects.equals(deliverId, that.deliverId) && Objects.equals(deliverLocation, that.deliverLocation) && Objects.equals(receive, that.receive) && Objects.equals(deliverFee, that.deliverFee) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(payState, that.payState) && Objects.equals(orderDate, that.orderDate) && Objects.equals(deliverName, that.deliverName) && Objects.equals(payBank, that.payBank) && Objects.equals(payNumber, that.payNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderId, productId, memberId, isReturn, managerId, deliverState, deliverId, deliverLocation, receive, deliverFee, totalPrice, payState, orderDate, deliverName, payBank, payNumber);
    }
}