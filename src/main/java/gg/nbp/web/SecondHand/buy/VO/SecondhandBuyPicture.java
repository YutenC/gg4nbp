package gg.nbp.web.SecondHand.buy.VO;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "secondhand_buy_picture" ,catalog = "five")
public class SecondhandBuyPicture implements Serializable{
	private static final long serialVersionUID = 9121233168593529556L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Integer imageId;
	
	@Column(name = "buylist_id")
	private Integer buylistId;
	
	
	private String image;
	
	
	public SecondhandBuyPicture(Integer buylistId, String image){
		this.buylistId = buylistId;
		this.image = image;
	}
	
	
}
