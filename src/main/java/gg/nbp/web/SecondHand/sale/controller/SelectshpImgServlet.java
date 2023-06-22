package gg.nbp.web.SecondHand.sale.controller;



import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/manager/shpImg_Select")
public class SelectshpImgServlet extends HttpServlet {

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


        List<SecondhandProductImage> imgs = SERVICE.selectimg(sp);
        ArrayList<String> imglist = new ArrayList<>();

        for (SecondhandProductImage img : imgs){
            imglist.add(img.getImage());
        }



        CommonUtil.writepojo2Json(resp, imglist);

    }
}
