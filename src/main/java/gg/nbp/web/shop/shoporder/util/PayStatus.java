package gg.nbp.web.shop.shoporder.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayStatus {
	UNPAID(1), PAID(2), PAIDONDELIVERED(3);
	
	private int value;
	
}
