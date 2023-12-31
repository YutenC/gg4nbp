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
import java.util.Iterator;
import java.util.List;


@WebServlet("/secondhand/shp_main")
public class MainViewServlet extends HttpServlet {
    // 找圖片
    // 找名字
    @Autowired
    private SecondhandProductService SERVICE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("image/gif");

        List<SecondhandProduct> secondhandproductList = SERVICE.searchLaunch();

        // 重新設定集合中物件的屬性(因關聯載入到sh_procudct_image，會有延遲載入的問題)
        Iterator<SecondhandProduct> iterator = secondhandproductList.iterator();
        List<SecondhandProduct> secondhandproductNewList = new ArrayList<>();
        while (iterator.hasNext()) {
            SecondhandProduct sp = iterator.next();
            SecondhandProduct newsp = new SecondhandProduct();
            newsp.setProductId(sp.getProductId());
            newsp.setName(sp.getName());

            //取第一張圖片
            List<SecondhandProductImage> imgs = SERVICE.selectimg(newsp);
//            imgs.get(0).getImage();
            newsp.setImage(imgs);

            secondhandproductNewList.add(newsp);
        }






        CommonUtil.writepojo2Json(resp, secondhandproductNewList);







    }
}



