package gg.nbp.web.SecondHand.sale.controller;

import java.io.IOException;
import java.util.List;

import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/shp_Edit")
public class EditshpServlet extends HttpServlet {
	@Autowired
	private SecondhandProductService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("image/*");

        boolean state = true;
        int shpproductId = 0;

        SecondhandProduct shp = CommonUtil.json2pojo(req, SecondhandProduct.class);

        shpproductId = shp.getProductId();

        shp = SERVICE.editshp(shp);

        if (shp.getName().trim().isEmpty() || shp.getContent().trim().isEmpty() || shp.getType().trim().isEmpty()){
            shp = new SecondhandProduct();
            shp.setMessage("無二手商品資訊");
            state = false;
        }


        // =========修改圖片(頁面能選擇刪除，但無法進資料庫修改)
//        List<SecondhandProductImage> newImages = shp.getImage();
//        if (newImages != null) {
//            for (SecondhandProductImage img : newImages) {
//                SERVICE.updateimg(img, shpproductId);
//            }
//        }
        // =========

        shp.setSuccessful(state);

        CommonUtil.writepojo2Json(resp, shp);

    }
}
