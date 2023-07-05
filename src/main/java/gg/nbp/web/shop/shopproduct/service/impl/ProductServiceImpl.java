package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shoporder.dao.ShoppingListDao;
import gg.nbp.web.shop.shoporder.entity.PKShoppingList;
import gg.nbp.web.shop.shoporder.entity.ShoppingList;
import gg.nbp.web.shop.shopproduct.dao.ProductDao;
import gg.nbp.web.shop.shopproduct.dao.ProductImageDao;
import gg.nbp.web.shop.shopproduct.dao.ProductRepository;
import gg.nbp.web.shop.shopproduct.entity.*;
import gg.nbp.web.shop.shopproduct.pojo.ProductSelect;
import gg.nbp.web.shop.shopproduct.redisdao.ProductRedisDao;
import gg.nbp.web.shop.shopproduct.service.FollowService;
import gg.nbp.web.shop.shopproduct.service.ProductService;
import gg.nbp.web.shop.shopproduct.util.ConstUtil;
import gg.nbp.web.shop.shopproduct.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

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

    @Autowired
    FollowService followService;

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> getAllProduct(Integer memId, Integer limit) {
        List<Product> products = productDao.selectAll();
//        setProductIndexImage(products);
        setFollows(memId,products);


        return products;
    }

    @Override
    public List<Product> getAllProductByCondition(Integer memId, ProductSelect productSelect) {
        List<Product> products =  productDao.selectByCondition(productSelect);

        List<String> required= productSelect.getRequired();
        if (required != null) {
            for(String key:required){
                if(products!=null && products.size()!=0){
                    if("follow".equals(key)){
                        setFollows(memId,products);
                    }
                    else {
                        Product product= products.get(0);
                        if(MyUtil.checkNULL(key,product,Product.class)){
                            MyUtil.runMethod(key,this,ProductServiceImpl.class,List.class,products);
                        }
                    }

                }
            }
        }


        return products;
    }

    @Override
    public List<Product> getAllProductWithIndexImg(Integer memId) {
        List<Product> products = productDao.selectAll();
        setProductIndexImage(products);
        setFollows(memId,products);
        return products;
    }

    @Override
    public List<Product> getProductByType(Integer memId,Integer type) {
        List<Product> products = productDao.selectByType(type);
        setProductIndexImage(products);
        setFollows(memId,products);
        return products;
    }


    @Override
    public List<Product> searchProducts(Integer memId,String search) {
        List<Product> products = productDao.searchProducts(search);
        setProductIndexImage(products);
        setFollows(memId,products);
        return products;
    }


    @Override
    public Product getProductById(Integer id) {
        return productDao.selectById(id);
    }

    @Override
    public ProductDetail getProductDetail(Integer memId,Integer id) {
        Product product = getProductById(id);
        setFollow_old(memId,product);

        List<ProductImage> productImages = getProductImgs(id);
        ProductDetail productDetail = new ProductDetail(product, productImages);

        return productDetail;
    }

    @Override
    public List<Product> getProductByBuyTimes(Map<String,Object> map,Integer type) {
        Integer limit=(Integer)map.get("limit");
        List<Product> products = productDao.selectByBuyTimes(limit,type);
        setProductIndexImage(products);

        Integer memId=  (Integer)map.get("memId");
        setFollows(memId,products);
        return products;
    }

    @Override
    public List<Product> getProductByBuyTimes(Integer amount, Integer type) {
        List<Product> products = productDao.selectByBuyTimes(amount, type);
        setProductIndexImage(products);
        return products;
    }

    @Override
    public int updateProductScore(Product product) {
        return productDao.updateProductScore(product);
    }

    @Override
    public int updateProductAmountBuyTimes(Product product) {
        return productDao.updateProductAmountBuyTimes(product);
    }

    @Override
    public int updateProductInfo(Product product) {
        return productDao.update(product);
    }


    @Override
    public List<Product> getProductHistory() {
        return productRedisDao.getHistoryProductBrowse();
    }

    @Override
    public void saveProductBrowseToRedis(Integer id) {
        Product product = getProductById(id);
        ProductImage productImage = getProductIndexImg(id);
        product.setProductIndexImage(productImage);
        productRedisDao.saveProductBrowseToRedis(product);
    }


    @Override
    public ProductImage getProductIndexImg(Integer id) {

        ProductImage productImage;
        try {
            List<ProductImage> productImages = productImageDao.getIndexImgByProductId(id);
            productImage=productImages.get(0);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            productImage = new ProductImage();
            productImage.setImage(ConstUtil.getDEFAULTIMG());
        }

        return productImage;
    }

    @Override
    public List<ProductImage> getProductImgs(Integer id) {
        List<ProductImage> productImages;
        try {
            productImages = productImageDao.selectByProductId(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            ProductImage productImage = new ProductImage();
            productImage.setImage(ConstUtil.getDEFAULTIMG());
            productImages = new ArrayList<>();
            productImages.add(productImage);
        }
        return productImages;
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



    private void setProductIndexImage(List<Product> products) {
        for (Product p : products) {
            ProductImage productImages = getProductIndexImg(p.getId());
            p.setProductIndexImage(productImages);
        }
    }

//, Map<String,Object> params
    private void setFollows(Integer memId,List<Product> products) {

        if(memId<0){
            return;
        }

        List<FollowList> followLists= followService.getFollowByMemberId(memId);
        HashMap<Integer,Product> map=new HashMap<>();

        for(int i=0;i<products.size();i++){
            map.put(products.get(i).getId(),products.get(i));
        }

        for(int i=0;i<followLists.size();i++){
            Product product= map.get(followLists.get(i).getId().getProductId());
            if(product!=null){
                product.setFollow(1);
            }
        }



//        for(int i=0;i<followLists.size();i++){
//            Integer productId=followLists.get(i).getId().getProductId();
//
//            for(int j=0;j<products.size();j++){
//
//            }
//        }

    }

    private void setFollow_old(Integer memId,Product product){
        if(memId<0){
            return;
        }

        List<FollowList> followLists= followService.getFollowByMemberId(memId);

        for(int i=0;i<followLists.size();i++){
            if(followLists.get(i).getId().getProductId()==product.getId()){
                product.setFollow(1);
            }

        }
    }

    private void setFollow(Integer memId,List<Product> products){
        if(memId<0){
            return;
        }

        List<FollowList> followLists= followService.getFollowByMemberId(memId);
        HashMap<Integer,Product> map=new HashMap<>();

        for(int i=0;i<products.size();i++){
            map.put(products.get(i).getId(),products.get(i));
        }

        for(int i=0;i<followLists.size();i++){
            Product product= map.get(followLists.get(i).getId().getProductId());
            product.setFollow(1);
        }
    }

}
