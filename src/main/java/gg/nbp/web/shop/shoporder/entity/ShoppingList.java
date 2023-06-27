package gg.nbp.web.shop.shoporder.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SHOPPING_LIST")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingList implements java.io.Serializable{
	
	private static final long serialVersionUID = -5891042733433329198L;

	@EmbeddedId
	private PKShoppingList pkShoppingList;
	
	private Integer quantity;
}
