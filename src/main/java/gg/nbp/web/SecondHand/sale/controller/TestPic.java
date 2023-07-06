package gg.nbp.web.SecondHand.sale.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.buy.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/manager/testpic")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 设置文件大小阈值
		maxFileSize = 1024 * 1024 * 5, // 设置最大文件大小
		maxRequestSize = 1024 * 1024 * 10 // 设置最大请求大小
)
public class TestPic extends HttpServlet {
	private static final long serialVersionUID = -6139457402422576192L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Collection<Part> parts = req.getParts();
		try {
			File file = new File(Constant.SAVE_URL);
			if (!file.exists()) {
				file.mkdirs();
			}
			for (Part part : parts) {
				String url = Constant.SAVE_URL + part.getName();
				File upfile = new File(url);
				try (InputStream reader = part.getInputStream();
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(upfile))) {
					byte[] buffer = new byte[1024];
					int s;
					while ((s = reader.read(buffer)) != -1) {
						bos.write(buffer, 0, s);

					}
					System.out.println("上傳完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
