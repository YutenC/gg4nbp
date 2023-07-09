package gg.nbp.web.shop.shopproduct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import gg.nbp.web.shop.shopproduct.service.ProductService;

@RestController
public class testProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getMem")
    public String getMember(){
//        List<Product> list= productService.getAllProduct();
        return "ccc";
    }


//    @PostMapping("/getMem")
//    public String getMember(@PathVariable("id") int id){
////        List<Product> list= productService.getAllProduct();
//        return "ccc";
//    }

    @PostMapping("/getMem")
    public String getMember_(){
//        List<Product> list= productService.getAllProduct();
        return "ccc";
    }
}
