package gg.nbp.web.shop.shoporder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements java.io.Serializable{
	
	private static final long serialVersionUID = 1404904578291894640L;

	@EmbeddedId
	private PKOrderDeatail pkOrderDeatail;
	
	private Integer quantity;
	
	@Column(insertable = false)
	private Integer comment;
	
	@Column(name = "COMMENT_CONTENT", insertable = false)
	private String commentContent;
	
	@Column(name = "IS_RETURN")
	private Integer isReturn;
	
	@Column(name = "EXCHANGE_TIME")
	private Integer exchangeTime;
	
	@Column(name = "MANAGERS_ID")
	private Integer managerId;
	
	@Column(name = "TOTAL_PRICE")
	private Integer totalPrice;

}
