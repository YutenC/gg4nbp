package gg.nbp.web.shop.shoporder.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransOrderProduct {
		
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
