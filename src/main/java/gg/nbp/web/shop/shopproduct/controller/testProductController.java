package gg.nbp.web.shop.shopproduct.controller;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/updateProductAmountBuyTimes")
    public String updateProductAmountBuyTimes(){
//        List<Product> list= productService.getAllProduct();
        Product product=new Product();
        product.setId(29);
        product.setAmount(234);
        product.setBuyTimes(12);
        productService.updateProductAmountBuyTimes(product);


        return "ccc";
    }


    @PostMapping("/getMem")
    public String getMember_(){
//        List<Product> list= productService.getAllProduct();
        return "ccc";
    }
}
