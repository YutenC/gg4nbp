package gg.nbp.web.SecondHand.sale.dao;


import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandOrder;

import java.util.List;

public interface SecondhandOrderDao extends CoreDao<SecondhandOrder, Integer> {

    @Override
    int insert(SecondhandOrder pojo);

    @Override
    int deleteById(Integer orderId);

    @Override
    int update(SecondhandOrder pojo);

    @Override
    SecondhandOrder selectById(Integer orderId);

    @Override
    List<SecondhandOrder> selectAll();

    List<SecondhandOrder> selectByMemId(Integer memberId);
}
