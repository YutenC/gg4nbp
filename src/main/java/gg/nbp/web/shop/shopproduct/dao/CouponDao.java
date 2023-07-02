package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.shop.shopproduct.core.CoreDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.pojo.DaoConditionSelect;

import java.util.List;

public interface CouponDao extends CoreDao<Coupon,Integer> {
    Coupon selectByDiscountCode(String discountCode);

    List<Coupon> selectByCondition(DaoConditionSelect daoSelect);
}
