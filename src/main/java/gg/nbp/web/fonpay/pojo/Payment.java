package gg.nbp.web.fonpay.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
@Data
public class Payment implements Serializable{
	private static final long serialVersionUID = -758829582801950546L;
	private String paymentTransactionId;
	private String paymentTransactionNumber;
	private String paymentNo;
	private String legacyId;
	private String providerName;
	private String merchantCode;
	private String merchantName;
	private String paymentSettingName;
	private String merchantHandlingCharge;
	private String itemName;
	private Integer totalPrice;
	private String status;
	private String statusName;
	private String memberName;
	private Timestamp paidDate;
	private Timestamp requestDate;
	private Timestamp settledDate;
	private Timestamp paymentDueDate;
	private Timestamp cancelDate;
	private Timestamp paidConfirmDate;
	private Boolean cancellable;
	private Boolean refundable;
	private Integer remainPrice;
	private String paymentUrl;
	private List<Object> refundTransactionRecordList;
	private String creditCardNo;
	private String approveCode;
	
	
	
	
}
