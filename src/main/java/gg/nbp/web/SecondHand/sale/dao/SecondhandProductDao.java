package gg.nbp.web.SecondHand.sale.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProduct;


public interface SecondhandProductDao extends CoreDao<SecondhandProduct, Integer> {



    @Override
    default int insert(SecondhandProduct secondhandproduct) {
        return 0;
    }

    @Override
    default int deleteById(Integer productId) {
        return 0;
    }

    @Override
    default int update(SecondhandProduct secondhandproduct) {
        return 0;
    }

    @Override
    default SecondhandProduct selectById(Integer productId) {
        return null;
    }

    @Override
    default List<SecondhandProduct> selectAll() {
        return null;
    }

    List<SecondhandProduct> selectByTime();

    List<SecondhandProduct> selectLaunch();


}




