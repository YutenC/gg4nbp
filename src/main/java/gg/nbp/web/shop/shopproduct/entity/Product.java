package gg.nbp.web.shop.shopproduct.entity;

import java.util.List;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "five")
public class Product {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "Product_name", nullable = false, length = 80)
    private String productName;

    @Expose
    @Column(name = "Type", nullable = false)
    private Integer type;

    @Expose
    @Column(name = "Price", nullable = false)
    private Integer price;

    @Expose
    @Column(name = "Amount", nullable = false)
    private Integer amount;

    @Expose
    @Column(name = "Buy_Times", nullable = false)
    private Integer buyTimes;

    @Expose
    @Column(name = "Brand", length = 45)
    private String brand;

    @Expose
    @Column(name = "Rate", nullable = false)
    private Integer rate;

    @Expose
    @Column(name = "Reviewe_count", nullable = false)
    private Integer revieweCount;

    @Expose
    @Lob
    @Column(name = "Content")
    private String content;

    @Expose
    @Column(name = "Launch_Time")
    private java.util.Date launchTime;

    @Expose
    @Column(name = "Takeoff_Time",insertable = false)
    private java.util.Date takeoffTime;

    @Expose
    @Column(name = "state",insertable = false)
    private Integer state;


    @Expose
    @Transient
    ProductImage productIndexImage;

    @Transient
    List<ProductImage> productImages;

    @Expose
    @Transient
    int follow;
}