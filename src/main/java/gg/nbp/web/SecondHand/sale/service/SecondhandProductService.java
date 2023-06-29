package gg.nbp.web.SecondHand.sale.service;

import java.util.List;

import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;

public interface SecondhandProductService {


    SecondhandProduct addshp(SecondhandProduct secondhandproduct);

    SecondhandProduct editshp(SecondhandProduct secondhandproduct);

    boolean delete(Integer productID);

    SecondhandProduct selectOne(Integer productID);

    List<SecondhandProduct> searchAll();

    List<SecondhandProduct> searchTime();

    List<SecondhandProduct> searchLaunch();

    List<SecondhandProduct> searchByType(String type);

    List<SecondhandProduct> searchByName(String keyword);




    public boolean insertimg(SecondhandProductImage img , Integer id);

    public boolean deleteimg(SecondhandProductImage img);

    public boolean updateimg(SecondhandProductImage img, Integer id);

    public List<SecondhandProductImage> selectimg(SecondhandProduct shp) ;

}
