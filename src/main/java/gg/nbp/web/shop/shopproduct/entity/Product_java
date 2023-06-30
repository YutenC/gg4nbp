package gg.nbp.web.shop.shopproduct.entity;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

 import jakarta.persistence.*;
import java.util.List;

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
    @Column(name = "Type", nullable = false, length = 8)
    private String type;

    @Expose
    @Column(name = "Price", nullable = false)
    private Integer price;

    @Expose
    @Column(name = "Amount", nullable = false)
    private Integer amount;

    @Expose
    @Column(name = "Buy_Times")
    private Integer buyTimes;

    @Expose
    @Column(name = "Brand", length = 45)
    private String brand;

    @Expose
    @Column(name = "Rate")
    private Integer rate;

    @Expose
    @Column(name = "Reviewe_count")
    private Integer revieweCount;

    @Expose
    @Lob
    @Column(name = "Content")
    private String content;

    @Expose
    @Column(name = "Launch_Time")
    private java.util.Date launchTime;

    @Expose
    @Transient
    ProductImage productIndexImage;

    @Transient
    List<ProductImage> productImages;

    @Expose
    @Transient
    int follow;

}