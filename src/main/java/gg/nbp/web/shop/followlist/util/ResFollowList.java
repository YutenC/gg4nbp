package gg.nbp.web.shop.followlist.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
