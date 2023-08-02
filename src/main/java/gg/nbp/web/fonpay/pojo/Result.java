package gg.nbp.web.fonpay.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import lombok.Data;

@Data
public class Result implements Serializable{
	private static final long serialVersionUID = 7827396877867587663L;
	private Payment payment;
	private String paymentTransactionId;
	private String refundNo;
	private Integer refundPrice;
	private Timestamp requestDate ;
	private Timestamp refundRequestDate;
	private Timestamp refundDate;
	private String status;
	private String statusName;
	private String name;
	private String mode;
	private String type;
	private String provider;
	private BigDecimal handlingChargeRate;
	private BigDecimal handlingChargeFee;
	private String paymentConfirmRequestTime;
	private String paymentConfirmTime;
	private String paymentConfirmMode;
	private String paymentRefundMode;
	private Map<String, String> paymentSettingData;
}
