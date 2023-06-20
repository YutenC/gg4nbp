package gg.nbp.web.SecondHand.sale.dao;

import java.util.List;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.SecondHand.sale.entity.SecondhandProductImage;

public interface SecondhandProductImageDao extends CoreDao<SecondhandProductImage, Integer> {

    @Override
    default int insert(SecondhandProductImage secondhandproductimage) {
        return 0;
    }

    // 刪除該筆商品全部圖片
    @Override
    default int deleteById(Integer productId) {
        return 0;
    }

    // 更新該筆商品全部圖片
    @Override
    default int update(SecondhandProductImage productId) {
        return 0;
    }

    // 選取該筆商品單一圖片
    @Override
    default SecondhandProductImage selectById(Integer imageId) {
        return null;
    }

    // 選取所有商品全部圖片
    @Override
    default List<SecondhandProductImage> selectAll() {
        return null;
    }


//     選取該筆商品全部圖片
    List<SecondhandProductImage> selectByProId(Integer productId);
}
