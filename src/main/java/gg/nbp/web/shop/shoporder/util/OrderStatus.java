package gg.nbp.web.shop.shoporder.util;

import lombok.Getter;

@Getter
public enum OrderStatus {
	DONE(1), CANCELED(2), APPLYCANCLED(3), APPLYRETURN(4);
	
	private int value;
	
	private OrderStatus(int value) {
		this.value = value;
	}
	
}
