package gg.nbp.web.SecondHand.sale.entity;


import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import gg.nbp.core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "secondhand_product", schema = "five")
public class SecondhandProduct extends Core {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private Integer price;

    @Column
    private String content;

    @Column(name = "launch_time")
    @CreationTimestamp
    private Date launchTime;

    @Column(name = "Is_launch")
    private String isLaunch = "1";

//    @OneToMany(mappedBy = "secondhandproduct")
//    private List<SecondhandProductImage> secondhandproductimages;

    @Transient
    @OneToMany
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private List<SecondhandProductImage> image ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SecondhandProduct that = (SecondhandProduct) o;
        return Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(price, that.price) && Objects.equals(content, that.content) && Objects.equals(launchTime, that.launchTime) && Objects.equals(isLaunch, that.isLaunch) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productId, name, type, price, content, launchTime, isLaunch, image);
    }
}
