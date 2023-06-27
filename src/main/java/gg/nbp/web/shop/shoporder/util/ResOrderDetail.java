package gg.nbp.web.shop.shoporder.util;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class ResOrderDetail implements Serializable{
	
	private static final long serialVersionUID = 3993304797750363578L;

	private Integer productId;
	
	private String productName;
	
	private Integer productPrice;
	
	private Integer productAmount;
	
	private java.sql.Timestamp purchaseDate;
}
