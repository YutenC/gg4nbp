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

@WebServlet("/manager/shp_delete")
public class DeleteshpServlet extends HttpServlet {
	@Autowired
	private SecondhandProductService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        SecondhandProduct secondhandproduct = CommonUtil.json2pojo(req, SecondhandProduct.class);

        List<SecondhandProductImage> imgs = SERVICE.selectimg(secondhandproduct);
        for(SecondhandProductImage img: imgs){
            SERVICE.deleteimg(img);
        }

        SERVICE.delete(secondhandproduct.getProductId());
    }
}
