package gg.nbp.web.shop.shoporder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ORDER_MASTER")
public class OrderMaster implements java.io.Serializable{
	
	private static final long serialVersionUID = 3814433510362508631L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID", insertable = false, updatable = false)
	private Integer orderId;
	
	@Column(name = "MEMBER_ID", updatable = false)
	private Integer memberId;
	
	@Column(name = "COMMIT_DATE", updatable = false)
	private java.sql.Timestamp commitDate;	// 訂單成立日期
	
	@Column(name = "TOTAL_PRICE", updatable = false)
	private Integer totalPrice;
	
	@Column(name = "BONUS_USE", updatable = false)
	private Integer bonusUse;		// 使用紅利
	
	@Column(name = "DELIVER_NUMBER")
	private String deliverNumber;		// 物流編號
	
	@Column(name = "COMMIT_TYPE")		// 付款方式
	private Integer commitType;
	
	@Column(name = "DELIVER_STATE")
	private Integer deliverState;		// 出貨狀態
	
	@Column(name = "DELIVER_FEE")
	private Integer deliverFee;
	
	@Column(name = "DELIVER_LOCATION")
	private String deliverLocation;
	
	@Column(name = "PICK_TYPE")
	private Integer pickType;		// 取貨方式
	
	@Column(name = "FINISH_TIME")
	private java.sql.Timestamp finishTime;	// 訂單結案時間

	@Column(name = "COUPON_ID", updatable = false)
	private Integer couponId;
	
	@Column(name = "ORDER_STATUS")
	private Integer orderStatus;	// 訂單狀態：1成立 2取消 3申請取消 4申請退貨
	
	@Column(name = "PAY_STATUS")
	private Integer payStatus;		// 付款狀態：1待付款 2已付款 3貨到付款
	
//	@OneToMany
//	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
//	private List<OrderDetail> odList;
}
