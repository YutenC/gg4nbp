package gg.nbp.web.SecondHand.sale.entity;


import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer isLaunch = 0;

//    @OneToMany(mappedBy = "secondhandproduct")
//    private List<SecondhandProductImage> secondhandproductimages;

}
