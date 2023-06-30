package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.web.Member.util.MemberCommonUitl;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.*;
import java.util.Collection;

@WebServlet("/manager/testpic")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 设置文件大小阈值
		maxFileSize = 1024 * 1024 * 5, // 设置最大文件大小
		maxRequestSize = 1024 * 1024 * 10 // 设置最大请求大小
)
public class TestPic extends HttpServlet {
	private static final long serialVersionUID = -6139457402422576192L;

	@Autowired
	private SecondhandProductService SERVICE;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Collection<Part> parts = req.getParts();
		try {

			File file = new File("src/main/resources/static/img");
			if (!file.exists()) {
				file.mkdirs();
			}

			for (Part part : parts) {
//				String url = req.getContextPath() + "src/main/resources/static/img" + part.getName();



				String storageFolder = "src/main/resources/static/img/secondHand";
				//      定義圖片存儲的資料夾路徑（相對於專案的位置）

				String storagePath = System.getProperty("user.dir") + "/" + storageFolder;
				//      獲取目標資料夾的動態路徑（相對於專案的位置）

				String imgName = part.getSubmittedFileName();
				//      獲取圖片的檔名

				String absolutePath = MemberCommonUitl.getAbsolutePath(storagePath);
				//      將相對路徑轉換為絕對路徑

				String url = absolutePath + File.separator + imgName;
				//      將圖片儲存到目標路徑



				File upfile = new File(url);
				try (InputStream reader = part.getInputStream();
					 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(upfile)))
				{
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
