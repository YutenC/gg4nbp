package gg.nbp.web.SecondHand.sale.service;

import java.util.List;

import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;

public interface SecondhandOrderService {

    SecondhandOrder addOd(SecondhandOrder od);

    boolean delOd(Integer orderId);

    SecondhandOrder editOd(SecondhandOrder od);

    SecondhandOrder selectOne(Integer orderId);

    List<SecondhandOrder> selectAll();

    List<SecondhandOrder> selectMem(Integer memId);




}
