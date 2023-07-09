package gg.nbp.web.SecondHand.sale.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gg.nbp.web.SecondHand.buy.util.Constant;
import gg.nbp.web.SecondHand.sale.dao.SecondhandProductDao;
import gg.nbp.web.SecondHand.sale.dao.SecondhandProductImageDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;
import gg.nbp.web.SecondHand.sale.service.SecondhandProductService;

@Transactional
@Service
public class SecondhandProductServiceImpl implements SecondhandProductService {

	@Autowired
    private SecondhandProductDao shpdao ;

    @Autowired
    private SecondhandProductImageDao shpdaoPic;


    @Override
    public SecondhandProduct addshp(SecondhandProduct secondhandproduct) {
        if ((secondhandproduct.getName().trim().isEmpty())) {
            secondhandproduct.setMessage("請輸入二手商品名稱");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if ((secondhandproduct.getType().isEmpty())) {
            secondhandproduct.setMessage("請選擇二手商品欄位");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if (secondhandproduct.getPrice() == null) {
            secondhandproduct.setMessage("請輸入二手商品價格");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        } else if (secondhandproduct.getPrice() <= 0) {
            secondhandproduct.setMessage("價格輸入錯誤");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        if ((secondhandproduct.getContent().trim().isEmpty())) {
            secondhandproduct.setMessage("請輸入二手商品內容");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        }

        final int resultCount = shpdao.insert(secondhandproduct);
        if (resultCount < 1) {
            secondhandproduct.setMessage("商品上架失敗");
            secondhandproduct.setSuccessful(false);
            return secondhandproduct;
        } else {
            secondhandproduct.setMessage("商品上架成功");
            secondhandproduct.setSuccessful(true);
            return secondhandproduct;
        }
    }


    @Override
    public SecondhandProduct editshp(SecondhandProduct secondhandproduct) {
        final SecondhandProduct oshproduct = shpdao.selectById(secondhandproduct.getProductId());

        if (secondhandproduct.getName() != null) {
            oshproduct.setName(secondhandproduct.getName());
        }
        if (secondhandproduct.getType() != null) {
            oshproduct.setType(secondhandproduct.getType());
        }
        if (secondhandproduct.getPrice() != null) {
            oshproduct.setPrice(secondhandproduct.getPrice());
        }
        if (secondhandproduct.getContent() != null) {
            oshproduct.setContent(secondhandproduct.getContent());
        }
        if (secondhandproduct.getIsLaunch() != null) {
            oshproduct.setIsLaunch(secondhandproduct.getIsLaunch());
        }


        final int resultCount = shpdao.update(oshproduct);
        oshproduct.setSuccessful(resultCount > 0);
        oshproduct.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
        return oshproduct;
    }





    @Override
    public boolean delete(Integer productID) {
        shpdao.deleteById(productID);
        return true;
    }

    @Override
    public SecondhandProduct selectOne(Integer productID) {
        return shpdao.selectById(productID);
    }

    @Override
    public List<SecondhandProduct> searchAll() {

        return shpdao.selectAll();
    }

    @Override
    public List<SecondhandProduct> searchTime() {
        List<SecondhandProduct> shp = shpdao.selectByTime();
        List<SecondhandProduct> newshp = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            newshp.add(shp.get(i));
        }
        return newshp;
    }

    @Override
    public List<SecondhandProduct> searchLaunch() {
        return shpdao.selectLaunch();
    }

    @Override
    public List<SecondhandProduct> searchByType(String type) {
        return  shpdao.searchType(type);
    }

    @Override
    public List<SecondhandProduct> searchByName(String keyword) {
        return shpdao.searchKeyWord(keyword);
    }


    //================圖片======================

    @Override
    public boolean insertimg(SecondhandProductImage img, Integer id) {

        String url = Constant.SAVE_URL + img.getImage();

        SecondhandProductImage pic = new SecondhandProductImage();
        pic.setProductId(id);
        pic.setImage(url);
        shpdaoPic.insert(pic);
        return true;
    }

    // check deletebyid可否用productid
//    @Override
//    public boolean deleteimg(SecondhandProduct shp) {
//        shpdaoPic.deleteById(shp.getProductId());
//        return true;
//    }

    //    ???? 先選擇 再刪除
    @Override
    public boolean deleteimg(SecondhandProductImage img) {
        shpdaoPic.deleteById(img.getImageId());

        return true;
    }

    @Override
    public boolean updateimg(SecondhandProductImage img, Integer id) {
//        String url = Constant.SAVE_URL + img.getImage();
        img = shpdaoPic.selectById(id);
        SecondhandProductImage pic = new SecondhandProductImage();
//        pic.setProductId(id);
        pic.setImage(img.getImage());
        shpdaoPic.update(pic);
        return true;
    }


    @Override
    public List<SecondhandProductImage> selectimg(SecondhandProduct shp) {
        return shpdaoPic.selectByProId(shp.getProductId());
    }



}// 關鍵字搜尋?
