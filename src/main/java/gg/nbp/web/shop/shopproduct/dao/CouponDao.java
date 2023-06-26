package gg.nbp.web.shop.shopproduct.dao;

import gg.nbp.web.shop.shopproduct.core.CoreDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;

public interface CouponDao extends CoreDao<Coupon,Integer> {
    Coupon selectByDiscountCode(String discountCode);
}
