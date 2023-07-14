package gg.nbp.web.shop.shopproduct.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_image", schema = "five")
public class ProductImage {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Product_id", nullable = false)
    private Product product;

    @Expose
    @Column(name = "Image", length = 2048)
    private String image;

    @Expose
    @Column(name = "state")
    private Integer state;


    @Transient
    private String name;

    public ProductImage() {

    }

    public ProductImage(Product product, String image) {
        this.product = product;
        this.image = image;
    }
}