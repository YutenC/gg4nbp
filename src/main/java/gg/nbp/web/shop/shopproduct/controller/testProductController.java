package gg.nbp.web.shop.shopproduct.controller;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getMem")
    public String getMember(){
//        List<Product> list= productService.getAllProduct();
        return "ccc";

    }
}
