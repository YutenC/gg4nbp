package gg.nbp.web.SecondHand.sale.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.impl.SecondhandProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/sh_shop/priceChange")
public class PriceServlet extends HttpServlet {

//product price
//selverstate
//加總
//response

    @Autowired
    private SecondhandProductServiceImpl SERVICE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 從請求中獲取JSON數據
        BufferedReader reader = req.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        reader.close();

        // 解析JSON數據
        JsonObject jsonObject = JsonParser.parseString(jsonBody.toString()).getAsJsonObject();

        int productId = jsonObject.get("productId").getAsInt();
        String receive = jsonObject.get("receive").getAsString();
        int totalPrice = 0;
        int price = 0;

        SecondhandProduct shp = new SecondhandProduct();
        shp = SERVICE.selectOne(productId);
        price = shp.getPrice();

        if (receive.equals("1")){

            totalPrice = price + 100;

        } else if (receive.equals("2")){

            totalPrice = price + 60;

        }

        CommonUtil.writepojo2Json(resp, totalPrice);

    }


}
