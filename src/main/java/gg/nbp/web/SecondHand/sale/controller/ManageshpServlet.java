package gg.nbp.web.SecondHand.sale.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gg.nbp.core.util.CommonUtil;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manager/sh_productmanage")
public class ManageshpServlet extends HttpServlet {
	@Autowired
	private SecondhandProductService SERVICE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<SecondhandProduct> secondhandproductList = SERVICE.searchAll();

        // 重新設定集合中物件的屬性(因關聯載入到sh_procudct_image，會有延遲載入的問題)
        Iterator<SecondhandProduct> iterator = secondhandproductList.iterator();
        List<SecondhandProduct> secondhandproductNewList = new ArrayList<>();
        while (iterator.hasNext()) {
            SecondhandProduct sp = iterator.next();
            SecondhandProduct newsp = new SecondhandProduct();
            newsp.setProductId(sp.getProductId());
            newsp.setIsLaunch(sp.getIsLaunch());
            newsp.setName(sp.getName());
            newsp.setType(sp.getType());
            newsp.setPrice(sp.getPrice());
//            newsp.setLaunchTime(sp.getLaunchTime());
            secondhandproductNewList.add(newsp);
            }

        CommonUtil.writepojo2Json(resp, secondhandproductNewList);

        }
    }

