package gg.nbp.web.SecondHand.sale.service;

import java.util.List;

import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;

public interface SecondhandProductService {



SecondhandProduct addshp(SecondhandProduct secondhandproduct);



SecondhandProduct editshp(SecondhandProduct secondhandproduct);

boolean delete(Integer productID);

SecondhandProduct selectOne(Integer productID);

List<SecondhandProduct> searchAll();

}
