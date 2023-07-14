package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.CouponState;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;

    public CouponServiceImpl() {
    }


    @Override
    public Coupon getCouponById(Integer id) {
        Coupon coupon = couponDao.selectById(id);
        return coupon;
    }

    @Override
    public Coupon getCouponByDiscountCode(String discountCode) {
        Coupon coupon = null;
        try {
            coupon = couponDao.selectByDiscountCode(discountCode);
            if (coupon.getState() != CouponState.publish.getValue()) {//使用者使用，會判斷折價券是否有失效
                coupon = null;
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return coupon;
    }

    @Override
    public int addCoupon(Coupon coupon) {
        return couponDao.insert(coupon);
    }

    @Override
    public boolean deleteCoupon(Integer id) {
        couponDao.deleteById(id);
        return false;
    }

}
