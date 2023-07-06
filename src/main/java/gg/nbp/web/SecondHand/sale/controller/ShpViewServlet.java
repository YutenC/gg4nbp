package gg.nbp.web.SecondHand.sale.controller;


import gg.nbp.core.pojo.OneString;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.Manager.entity.Manager;
import gg.nbp.web.SecondHand.buy.util.Constant;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@WebServlet("/secondhand/shp_view")
public class ShpViewServlet extends HttpServlet {

    @Autowired
    private SecondhandProductService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("image/gif");

        // 先拿session
        // 再拿id 呼叫edit方法
        // json回傳物件

        SecondhandProduct sp = CommonUtil.json2pojo(req, SecondhandProduct.class);
        sp = SERVICE.selectOne(sp.getProductId());

        SecondhandProduct newsp = new SecondhandProduct();

        newsp.setName(sp.getName());
        newsp.setPrice(sp.getPrice());
        newsp.setContent(sp.getContent());

        List<SecondhandProductImage> imgs = SERVICE.selectimg(sp);
        newsp.setImage(imgs);

        CommonUtil.writepojo2Json(resp, newsp);





    }
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getParameter("url");

		resp.setContentType("image/gif");

		File src = new File(Constant.SAVE_URL +url);
		if (!src.exists()) {
			src = new File(Constant.SAVE_URL + "/Nofound.png");
		}

		try (ServletOutputStream out = resp.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))) {
			out.write(bis.readAllBytes());
		} catch (Exception e) {
//		e.printStackTrace();
		}

	}


}
