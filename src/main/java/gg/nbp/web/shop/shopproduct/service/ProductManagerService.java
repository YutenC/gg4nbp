package gg.nbp.web.shop.shopproduct.service;

import gg.nbp.web.shop.shopproduct.pojo.ProductPojo;

public interface ProductManagerService {

    void createProductFromcsv();

    void longTimeProcess();

    void addProduct(ProductPojo productPojo);

    void takeOnProduct(Integer id);

    void cancelTakeOnProduct(Integer id);

    void takeOffProduct(Integer id);

    void removeTakeOningProduct(Integer id);

    void removeTakeOffingProduct(Integer id);
}
