package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shoporder.dao.ShoppingListDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shopproduct.dao.FollowDao;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.entity.*;
import gg.nbp.web.shop.shopproduct.redisdao.ProductRedisDao;
import gg.nbp.web.shop.shopproduct.service.FollowService;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ObjectInstance;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImageDao productImageDao;

    @Autowired
    ProductRedisDao productRedisDao;

    @Autowired
    ShoppingListDao shoppingListDao;

//    @Autowired
//    FollowDao followDao;

    @Autowired
    FollowService followService;

    public ProductServiceImpl() {
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = productDao.selectAll();
        setProductIndexImg(products);
        return products;
    }

    @Override
    public List<Product> getProductByType(Integer type) {
        List<Product> products = productDao.selectByType(type.toString());
        setProductIndexImg(products);
        return products;
    }

    @Override
    public ProductImage getProductIndexImg(Integer id) {
        ProductImage productImage = productImageDao.getIndexImgByProductId(id);
        return productImage;
    }

    @Override
    public List<Product> searchProducts(String search) {
        List<Product> products = productDao.searchProducts(search);
        setProductIndexImg(products);
        return products;
    }


    @Override
    public Product getProductById(Integer id) {
        return productDao.selectById(id);
    }

    @Override
    public ProductDetail getProductDetail(Integer id) {
        Product product = productDao.selectById(id);
        List<ProductImage> productImages = productImageDao.selectByProductId(id);

        ProductDetail productDetail = new ProductDetail(product, productImages);

        return productDetail;
    }

    @Override
    public List<Product> getProductHistory() {
        return productRedisDao.getHistoryProductBrowse();
    }

    @Override
    public void saveProductBrowseToRedis(Integer id) {
        Product product = productDao.selectById(id);
        ProductImage productImage = productImageDao.getIndexImgByProductId(id);
        product.setProductIndexImage(productImage);
        productRedisDao.saveProductBrowseToRedis(product);
    }

    @Override
    public void addCart(Integer productId, Integer memId) {
        PKShoppingList pkShoppingList = new PKShoppingList(memId, productId);
        ShoppingList shoppingList;

        shoppingList = shoppingListDao.selectByCompositePk(pkShoppingList);

        if (shoppingList == null) {
            shoppingList = new ShoppingList(pkShoppingList, 1);
            shoppingListDao.insert(shoppingList);
        } else {
            shoppingList.setQuantity(shoppingList.getQuantity() + 1);
            shoppingListDao.update(shoppingList);
        }

    }



    @Override
    public List<Product> getProductByBuyTimes(Integer type) {
        List<Product>  products= productDao.selectByBuyTimes(type.toString());
        setProductIndexImg(products);
        return products;
    }

    @Override
    public List<Product> getProductByBuyTimes(Integer amount, Integer type) {
        List<Product>  products= productDao.selectByBuyTimes(amount,type.toString());
        setProductIndexImg(products);
        return null;
    }

    @Override
    public int updateProductScore(Product product) {

        return productDao.updateProductScore(product);
    }

    @Override
    public int updateProductAmountBuyTimes(Product product) {
        return productDao.updateProductAmountBuyTimes(product);
    }


//    private ProductImage getProductIndexImg_(Product product) {
//        List<ProductImage> productImages = productImageDao.selectByProductId(product.getId());
//        for (ProductImage productImage : productImages) {
//            if (productImage.getImage().endsWith("index.PNG")) {
//                return productImage;
//            }
//        }
//
//        return null;
//    }


    private void setProductIndexImg(List<Product>  products){
        for (Product p : products) {
            ProductImage productImages = getProductIndexImg(p.getId());
            p.setProductIndexImage(productImages);
        }
    }

}
