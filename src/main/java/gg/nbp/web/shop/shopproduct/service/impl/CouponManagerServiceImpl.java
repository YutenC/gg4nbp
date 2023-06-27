package gg.nbp.web.shop.shopproduct.service.impl;

import gg.nbp.web.shop.shopproduct.dao.CouponDao;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.redisdao.CouponActivityRedisDao;
import gg.nbp.web.shop.shopproduct.service.CouponManagerService;
import gg.nbp.web.shop.shopproduct.service.CouponService;
import gg.nbp.web.shop.shopproduct.util.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CouponManagerServiceImpl implements CouponManagerService {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponActivityRedisDao couponActivityRedisDao;

    @Autowired
    CouponDao couponDao;

    public CouponManagerServiceImpl() {
    }

    @Override
    public void generateCouponActivity() {
        for (int i = 0; i < 10; i++) {
            CouponActivity couponActivity = new CouponActivity("Activity " + i, "MF");
            couponActivity.setCoupon(genFixCouponData(i));//genCouponData()
            addCouponActivity(couponActivity);
        }
    }

    @Override
    public void addCouponActivity(CouponActivity couponActivity) {
        Coupon coupon = couponActivity.getCoupon();
        couponService.addCoupon(coupon);

        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                Coupon coupon_=couponService.getCouponByDiscountCode(coupon.getDiscountCode());
                couponActivity.setCoupon(coupon_);
                couponActivityRedisDao.addCouponActivity(couponActivity);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);
    }

    @Override
    public List<CouponActivity> getAllCouponActivity() throws RuntimeException {
        return  couponActivityRedisDao.getAllCouponActivity();
    }

    @Override
    public List<CouponActivity> searchCouponActivity() {
        return null;
    }

    @Override
    public void updateCouponActivity(CouponActivity couponActivity) {
        Coupon coupon = couponActivity.getCoupon();
        couponDao.update(coupon);

        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                Coupon coupon_=couponService.getCouponByDiscountCode(coupon.getDiscountCode());
                couponActivity.setCoupon(coupon_);
                couponActivityRedisDao.updateCouponActivity(couponActivity);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);
    }

    @Override
    public boolean deleteCoupon(Integer couponId) {
        if(couponService.getCouponById(couponId)!=null){
            couponService.deleteCoupon(couponId);
        }



        RedisContent redisService = new RedisContent() {
            @Override
            public int run() {
                couponActivityRedisDao.deleteCouponActivity(couponId);
                return 0;
            }
        };
        RedisFactory.getRedisServiceInstance().registerRedisService(redisService);

        return true;
    }


    private Coupon genFixCouponData(Integer index){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(cal.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        String date = simpleDateFormat.format(cal.getTime());
        java.sql.Date deadline = java.sql.Date.valueOf(date);

        Coupon coupon;
        switch (index){
            case 0:
                coupon = new Coupon(100, 1000, deadline, "Qb12BJZO22");
                break;
            case 1:
                coupon = new Coupon(100, 2000, deadline, "Qb34FFFO11");
                break;
            case 2:
                coupon = new Coupon(50, 500, deadline, "Qb55ASZ678");
                break;
            case 3:
                coupon = new Coupon(150, 2000, deadline, "Qb56BGFO90");
                break;
            case 4:
                coupon = new Coupon(100, 500, deadline, "Qb77XCVO22");
                break;
            case 5:
                coupon = new Coupon(100, 1000, deadline, "Qb345XZO67");
                break;
            case 6:
                coupon = new Coupon(200, 1500, deadline, "Qb875IRO93");
                break;
            case 7:
                coupon = new Coupon(300, 3000, deadline, "Qb345VBO67");
                break;
            case 8:
                coupon = new Coupon(100, 600, deadline, "Qb245DYO88");
                break;
            default:
                coupon = new Coupon(100, 1000, deadline, "Qb345XZO67");
                break;

        }
        return coupon;

    }

    private Coupon genCouponData() {
        Integer discount = ((int) (Math.random() * 50 + 51));
        Integer condition_price = ((int) (Math.random() * 500 + 501));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(cal.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        String date = simpleDateFormat.format(cal.getTime());
        java.sql.Date deadline = java.sql.Date.valueOf(date);

        String discountCode = genCouponCode();
        System.out.println("discountCode: " + discountCode);
        Coupon coupon = new Coupon(discount, condition_price, deadline, discountCode);

        return coupon;
    }

    private String genCouponCode() {
        final int RANDOM_NUM = 8;
        int rangeNum = 62;
        char[] randomChar = new char[RANDOM_NUM];
        String str = "";
        for (int i = 0; i < RANDOM_NUM; i++) {
            int random = (int) (Math.random() * rangeNum);
            if (random < 10) {
                randomChar[i] = (char) ('0' + random);
            } else if (random < 36) {
                randomChar[i] = (char) ('A' + random - 10);
            } else if (random >= 36) {
                randomChar[i] = (char) ('a' + random - 36);
            } else {

            }
            str += randomChar[i];
        }
        return str;
    }


}
