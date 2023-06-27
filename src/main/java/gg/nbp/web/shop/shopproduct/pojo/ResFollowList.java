package gg.nbp.web.shop.shopproduct.pojo;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResFollowList implements java.io.Serializable{

    private static final long serialVersionUID = -7900068203749093670L;

    private Integer productId;

    private String productName;

    private Integer productPrice;

    private Integer productAmount;

    private String productImgUrl;
}

