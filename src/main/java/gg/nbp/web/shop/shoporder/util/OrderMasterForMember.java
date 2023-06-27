package gg.nbp.web.shop.shoporder.util;

import java.util.List;

import gg.nbp.web.shop.shoporder.entity.OrderMaster;
import lombok.Data;

@Data
public class OrderMasterForMember {
	
	private OrderMaster orderMaster;
	
	private List<TransOrderProduct> transOrderProduct;

}
