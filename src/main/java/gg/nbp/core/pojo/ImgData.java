package gg.nbp.core.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgData {
	
	private Integer id ;
	private String image;

	@Override
	public String toString() {
		return "ImgData [image=" + image + "]";
	}

	
}
