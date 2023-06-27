package gg.nbp.web.shop.shoporder.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TransOrderProduct implements java.io.Serializable{
	
	private static final long serialVersionUID = 5534147712252264664L;

	private Integer productId;
	
	private String productImgUrl;
	
	private String productName;
	
	private String brand;
	
	private Integer buyAmount;
	
	private Integer price;
	
	private Integer stockAmount;
	
	private boolean checked;
	
	private Integer isReturn;
	
	private Integer comment;
	
	private String commentContent;
}
