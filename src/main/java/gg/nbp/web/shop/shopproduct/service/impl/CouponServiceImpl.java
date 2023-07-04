package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;

    public CouponServiceImpl(){
    }

    @Override
    public List<Coupon> getAllCoupon() {
        return couponDao.selectAll();
    }




    @Override
    public Coupon getCouponById(Integer id) {
        Coupon coupon=couponDao.selectById(id);
        return coupon;
    }

    @Override
    public Coupon getCouponByDiscountCode(String discountCode) {
        Coupon coupon=null;
        try {
            coupon=couponDao.selectByDiscountCode(discountCode);
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
