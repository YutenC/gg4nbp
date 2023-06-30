package gg.nbp.web.SecondHand.sale.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

@WebServlet("/manager/shp_Edit")
public class EditshpServlet extends HttpServlet {

    @Autowired
	private SecondhandProductService SERVICE;

    @Autowired
    private SecondhandProductImageDaoImpl IMAGEDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean state = true;

        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        reader.close();

        // 將 JSON 字串轉換為對應的 Java 物件
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBody.toString(), JsonObject.class);
        int productId = jsonObject.get("productId").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String type = jsonObject.get("type").getAsString();
        int price = jsonObject.get("price").getAsInt();
        String content = jsonObject.get("content").getAsString();

        SecondhandProduct shp = SERVICE.selectOne(productId);
        shp.setName(name);
        shp.setType(type);
        System.out.println("type=" + type);
        shp.setPrice(price);
        shp.setContent(content);
        SERVICE.editshp(shp);

        // 讀取 image 陣列
        JsonArray imageArrayNew = jsonObject.get("newImage").getAsJsonArray();
        JsonArray imageArray = jsonObject.get("oldImage").getAsJsonArray();

        // 迭代處理 image 陣列中的每個元素
        // 1. 新增
        for (JsonElement elementNew : imageArrayNew) {
            JsonObject imageObject = elementNew.getAsJsonObject();
            SecondhandProductImage img = gson.fromJson(imageObject, SecondhandProductImage.class);

            // 讀取圖片物件的屬性值
            img.setProductId(productId);

            // 在這裡可以使用讀取到的值進行後續的處理
            IMAGEDAO.insert(img);
            System.out.println("圖片新增成功");
        }

        // 2. 刪除
        for (JsonElement element : imageArray) {
            JsonObject imageObject = element.getAsJsonObject();

            // 讀取圖片物件的屬性值
            int imageId = imageObject.get("imageId").getAsInt();
            String image = imageObject.get("image").getAsString();

            // 在這裡可以使用讀取到的值進行後續的處理
            IMAGEDAO.deleteById(imageId);
            System.out.println("圖片刪除成功");
        }


        shp.setSuccessful(state);

        CommonUtil.writepojo2Json(resp, shp);

    }
}
