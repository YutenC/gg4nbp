package gg.nbp.web.shop.shopproduct.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductList {

    @Expose
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
    @Column(name = "Price", nullable = false)
    private Integer follow;

}
