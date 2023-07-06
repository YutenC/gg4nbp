package gg.nbp.web.shop.shoporder.util;

public enum OrderSelection {

	ALL(1), UNPAID(2), PAID(3), PAIDONEDELI(4), DELIVERD(5), UNDELI(6), ARRIVED(7),
	DONE(8), CANCELED(9), APPLYCAN(10), APPLYRETURN(11);
	
	private int code;
	
	OrderSelection (int code) {
		this.code = code;
	}
	
	public int getCode () {
		return this.code;
	}
	
	public void setCode (int code) {
		this.code = code;
	}
}
