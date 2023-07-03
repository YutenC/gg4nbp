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

@WebServlet("/manager/shp_Select")
public class SelectshpServlet extends HttpServlet {
	@Autowired
	private SecondhandProductService SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("image/*");

        // 先拿session
        // 再拿id 呼叫edit方法
        // json回傳物件

        SecondhandProduct sp = CommonUtil.json2pojo(req, SecondhandProduct.class);
        sp = SERVICE.selectOne(sp.getProductId());

        // 轉換content的換行標籤
        sp.setContent(sp.getContent().replace("<br>", "\n"));

        List<SecondhandProductImage> imgs = SERVICE.selectimg(sp);
        sp.setImage(imgs);

//        SecondhandProduct newsp = new SecondhandProduct();
//        newsp.setProductId(sp.getProductId());
//        newsp.setIsLaunch(sp.getIsLaunch());
//        newsp.setName(sp.getName());
//        newsp.setType(sp.getType());
//        newsp.setPrice(sp.getPrice());
//        newsp.setContent(sp.getContent());

//            newsp.setLaunchTime(sp.getLaunchTime());



        CommonUtil.writepojo2Json(resp, sp);

    }
}
