package gg.nbp.web.shop.shoporder.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ShopPicOutput {
	
	@RequestMapping(value = {"/ShopPic/{filename}", "/ShopPic/{folder}/{filename}", "/ShopPic/{folder}/{folder2}/{filename}"},
					method = RequestMethod.GET,
					produces = MediaType.IMAGE_JPEG_VALUE)
	public void getPic(HttpServletResponse res,
						@PathVariable(required = false) String folder,
						@PathVariable(required = false) String folder2,
						@PathVariable String filename) throws IOException {
		String path = "static/" + (folder == null? "" : folder + "/") + (folder2 == null? "" : folder2 + "/") + filename;
		System.out.println(path);
		var imgFile = new ClassPathResource(path);
		
		res.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imgFile.getInputStream(), res.getOutputStream());
	}
}
