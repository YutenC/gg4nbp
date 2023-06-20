package gg.nbp.web.SecondHand.sale.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/sh_productmanageAdd")
public class AddshpServlet extends HttpServlet {
	@Autowired
	private SecondhandProductService SERVICE;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        SecondhandProduct secondhandproduct = CommonUtil.json2pojo(req, SecondhandProduct.class);

        if (secondhandproduct == null){
            secondhandproduct = new SecondhandProduct();
            secondhandproduct.setMessage("無二手商品資訊");
            secondhandproduct.setSuccessful(false);
            CommonUtil.writepojo2Json(resp, secondhandproduct);
            return;
        }

        secondhandproduct = SERVICE.addshp(secondhandproduct);
        CommonUtil.writepojo2Json(resp, secondhandproduct);
    }
}
