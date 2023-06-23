package gg.nbp.web.SecondHand.sale.service;

import gg.nbp.web.Member.entity.Bank;
import gg.nbp.web.Member.entity.Member;
import gg.nbp.web.Member.entity.Member_credit;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;

import java.util.List;

public interface SecondhandOrderService {

    SecondhandOrder addOd(SecondhandOrder od, Member mem, Bank bank, Member_credit credit);

    boolean delOd(Integer orderId);

    SecondhandOrder editOd(SecondhandOrder od, Member mem, Bank bank, Member_credit credit);

    SecondhandOrder selectOne(Integer orderId);

    List<SecondhandOrder> selectAll();

    List<SecondhandOrder> selectMem(Integer memId);




}
