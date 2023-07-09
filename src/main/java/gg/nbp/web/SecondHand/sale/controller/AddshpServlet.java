package gg.nbp.web.SecondHand.sale.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/sh_productmanageAdd")
public class AddshpServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8656609715677632498L;
	@Autowired
	private SecondhandProductService SERVICE;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int shpproductId = 0;
        SecondhandProduct shp = null;
        boolean state = true;


        shp = CommonUtil.json2pojo(req, SecondhandProduct.class);



        try {



            if (shp.getName().trim().isEmpty() || shp.getContent().trim().isEmpty() || shp.getType().trim().isEmpty()){
                shp = new SecondhandProduct();
                shp.setMessage("無二手商品資訊");
                state = false;
            }

            SERVICE.addshp(shp);
            shp.setSuccessful(state);
            shpproductId = shp.getProductId();

//            if (shp.isSuccessful()) {
//                if (req.getSession(false) != null) {
//                    req.changeSessionId();
//                }
//                final HttpSession session = req.getSession();
//                session.setAttribute("productId", shpproductId);
//            }

        } catch (Exception e) {
            shp = new SecondhandProduct();
            shp.setSuccessful(state);
        }




// 圖
//        for (SecondhandProductImage img : shp.getImage()) {
//            SERVICE.insertimg((SecondhandProductImage)img, shpproductId);
//        }

        List<SecondhandProductImage> images = shp.getImage();
        if (images != null) {
            for (SecondhandProductImage img : images) {
                SERVICE.insertimg(img, shpproductId);
            }
        }


        CommonUtil.writepojo2Json(resp, shp);

    }
}
