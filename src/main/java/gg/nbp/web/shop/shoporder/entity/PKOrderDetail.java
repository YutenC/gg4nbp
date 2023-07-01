package gg.nbp.web.shop.shoporder.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Table(name = "ORDER_DETAIL")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PKOrderDetail implements Serializable{
	
	private static final long serialVersionUID = 7275579179233163110L;

	@Column(name = "PRODUCT_ID", updatable = false)
	private Integer productId;
	
	@Column(name = "ORDER_ID", updatable = false)
	private Integer orderId;

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PKOrderDetail other = (PKOrderDetail) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId);
	}
	
}
