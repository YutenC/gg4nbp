package gg.nbp.web.SecondHand.sale.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.nbp.web.SecondHand.sale.dao.impl.SecondhandProductImageDaoImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditshpServlet extends HttpServlet {

    @Autowired
	private SecondhandProductService SERVICE;

    @Autowired
    private SecondhandProductImageDaoImpl IMAGEDAO;

    @PostMapping("/manager/shp_Edit")
    protected void doPost(@RequestBody String jsonBody) throws JsonProcessingException {

        boolean state = true;
        int shpproductId = 0;

//        SecondhandProduct shp = CommonUtil.json2pojo(req[0], SecondhandProduct.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonBody);

        List<String> delImage = objectMapper.convertValue(jsonNode.get("oldImage"), ArrayList.class);

        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.remove("oldImage");

        SecondhandProduct shp = objectMapper.convertValue(objectNode, SecondhandProduct.class);

        shpproductId = shp.getProductId();

        shp = SERVICE.editshp(shp);

        if (shp.getName().trim().isEmpty() || shp.getContent().trim().isEmpty() || shp.getType().trim().isEmpty()){
            shp = new SecondhandProduct();
            shp.setMessage("無二手商品資訊");
            state = false;
        }

        // ========新增圖片
        List<SecondhandProductImage> images = shp.getImage();
        if (images != null) {
            for (SecondhandProductImage img : images) {
                SERVICE.insertimg(img, shpproductId);
            }
        }

        // =========刪除圖片(頁面能選擇刪除，但無法進資料庫修改)

        List<SecondhandProductImage> allImgs = IMAGEDAO.selectAll();

        for(SecondhandProductImage img: allImgs){
            int imageId = img.getImageId();
            String url = img.getImage();
            for (String oldUrl: delImage){
                if (url.equals(oldUrl)){
                    IMAGEDAO.deleteById(imageId);
                }
            }
        }

//        List<SecondhandProductImage> newImages = shp.getImage();
//        if (newImages != null) {
//            for (SecondhandProductImage img : newImages) {
//                SERVICE.updateimg(img, shpproductId);
//            }
//        }
        // =========

//        shp.setSuccessful(state);
//
//        CommonUtil.writepojo2Json(resp, shp);

    }
}
