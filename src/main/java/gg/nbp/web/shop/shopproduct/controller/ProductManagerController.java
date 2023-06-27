package gg.nbp.web.shop.shopproduct.controller;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.pojo.RequestMsg;
import gg.nbp.web.shop.shopproduct.pojo.ProductPojo;
import gg.nbp.web.shop.shopproduct.service.ProductManagerService;
import gg.nbp.web.shop.shopproduct.util.ObjectInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductManagerController {

    @Autowired
    ProductManagerService productManagerService;
    public ProductManagerController() {
    }


    public void createProductFromcsv() {
        productManagerService.createProductFromcsv();
    }

    public String longTimeProcess(){
        productManagerService.longTimeProcess();
        RequestMsg requestMsg=new RequestMsg("longTime","longTimeProcess","");

        Gson gson=new Gson();
        return gson.toJson(requestMsg);
    }

    public void addProduct(ProductPojo productPojo){
        productManagerService.addProduct(productPojo);
    }

    public String getSomeProduct(){
return null;
    }

    public void takeOnProduct(Integer id) {
        productManagerService.takeOnProduct(id);
    }

    public void cancelTakeOnProduct(Integer id){
        productManagerService.cancelTakeOnProduct(id);
    }

    public void takeOffProduct(Integer id) {
        productManagerService.takeOffProduct(id);
    }
}
