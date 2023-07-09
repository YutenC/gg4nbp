package gg.nbp.web.SecondHand.sale.controller;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/manager/shp_launch")
public class LaunchshpServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2667937944184316971L;
	@Autowired
    private SecondhandProductService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        SecondhandProduct shp = CommonUtil.json2pojo(req, SecondhandProduct.class);
//        SecondhandProduct oshp = new SecondhandProduct();
//        oshp.setProductId(shp.getProductId());
//        oshp.setIsLaunch(shp.getIsLaunch());
        SERVICE.editshp(shp);


    }
}
