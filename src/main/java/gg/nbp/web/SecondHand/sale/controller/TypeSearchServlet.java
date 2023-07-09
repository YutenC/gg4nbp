package gg.nbp.web.SecondHand.sale.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/secondhand/shp_typeSearch")
public class TypeSearchServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4710969012957605839L;
	@Autowired
    private SecondhandProductServiceImpl SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SecondhandProduct shp = CommonUtil.json2pojo(req, SecondhandProduct.class);
        String typeValue = shp.getType();

        List<SecondhandProduct> shpType = SERVICE.searchByType(typeValue);
        List<SecondhandProduct> shpList = new ArrayList<>();

        // 把type商品的圖片找出來
//        List<SecondhandProductImage> imgs = new ArrayList<>();
        for(SecondhandProduct p : shpType){
           List<SecondhandProductImage> imgs = SERVICE.selectimg(p);
           p.setImage(imgs);
           shpList.add(p);
        }


        CommonUtil.writepojo2Json(resp, shpList);

    }
}

