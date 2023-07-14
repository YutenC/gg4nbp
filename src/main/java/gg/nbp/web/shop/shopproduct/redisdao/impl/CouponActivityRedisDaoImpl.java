package gg.nbp.web.shop.shopproduct.redisdao.impl;

import com.google.gson.Gson;
import gg.nbp.web.shop.shopproduct.entity.Coupon;
import gg.nbp.web.shop.shopproduct.entity.CouponActivity;
import gg.nbp.web.shop.shopproduct.redisdao.CouponActivityRedisDao;
import gg.nbp.web.shop.shopproduct.util.RedisFactory;
import gg.nbp.web.shop.shopproduct.util.ShopProductConst;
import gg.nbp.web.shop.shopproduct.util.StringToObjectUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import java.util.*;

@Component
public class CouponActivityRedisDaoImpl implements CouponActivityRedisDao {


    @Override
    public List<CouponActivity> getAllCouponActivity() throws JedisException {
        List<CouponActivity> couponActivities = new ArrayList<CouponActivity>();
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);

        Set<String> items = jedis.smembers("CouponActivity:outline");

        for (String str : items) {
            Map<String, String> couponActivityMap = jedis.hgetAll("CouponActivity:" + str);
            CouponActivity couponActivity = StringToObjectUtil.mapStringToEntity(couponActivityMap, CouponActivity.class);
            couponActivities.add(couponActivity);
        }

        return couponActivities;
    }

    @Override
    public List<CouponActivity> getCouponActivitiesByCoupons(List<Coupon> coupons) {
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);
        List<CouponActivity> couponActivities=new ArrayList<>();
        for(Coupon coupon:coupons){
            Map<String, String> couponActivityMap = jedis.hgetAll("CouponActivity:" + coupon.getId().toString());
            if(couponActivityMap!=null && couponActivityMap.size()!=0){
                CouponActivity couponActivity = StringToObjectUtil.mapStringToEntity(couponActivityMap, CouponActivity.class);
                couponActivities.add(couponActivity);
            }
        }

        return couponActivities;
    }

    @Override
    public CouponActivity getCouponActivityByCouponId(Integer couponId) {
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);
        Map<String, String> couponActivityMap = jedis.hgetAll("CouponActivity:" + couponId.toString());
        CouponActivity couponActivity = StringToObjectUtil.mapStringToEntity(couponActivityMap, CouponActivity.class);
        return couponActivity;
    }

    @Override
    public void addCouponActivity(CouponActivity couponActivity) {
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);

        String cp_id_str = null;
        cp_id_str = jedis.hget("CouponActivity:" + couponActivity.getCoupon().getId().toString(), "couponId");


        if (cp_id_str == null) {
            Gson gson = new Gson();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("activityName", couponActivity.getActivityName());
            map.put("activityCode", couponActivity.getActivityCode());
            map.put("couponId", couponActivity.getCoupon().getId().toString());
            map.put("coupon", gson.toJson(couponActivity.getCoupon()));

            jedis.hmset("CouponActivity:" + couponActivity.getCoupon().getId().toString(), map);

            jedis.sadd("CouponActivity:outline", couponActivity.getCoupon().getId().toString());
        } else {
            System.out.println("重複cp_id");
        }


    }

    @Override
    public void updateCouponActivity(CouponActivity couponActivity) {
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);
        String cp_id_str = null;

        cp_id_str = jedis.hget("CouponActivity:" + couponActivity.getCoupon().getId().toString(), "couponId");
        if (cp_id_str != null) {
            Gson gson = new Gson();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("activityName", couponActivity.getActivityName());
            map.put("activityCode", couponActivity.getActivityCode());
            map.put("couponId", couponActivity.getCoupon().getId().toString());
            map.put("coupon", gson.toJson(couponActivity.getCoupon()));

            jedis.hmset("CouponActivity:" + couponActivity.getCoupon().getId().toString(), map);

            jedis.sadd("CouponActivity:outline", couponActivity.getCoupon().getId().toString());
        } else {

        }

    }

    @Override
    public void deleteCouponActivity(Integer couponId) {
        Jedis jedis = RedisFactory.getRedisServiceInstance().getJedis(ShopProductConst.REDIS_SELECT_INDEX);

        Set<String> items = jedis.smembers("CouponActivity:outline");
        for (String str : items) {
            Map<String, String> couponActivityMap = jedis.hgetAll("CouponActivity:" + str);
            int field = Integer.parseInt(couponActivityMap.get("couponId"));

            if (field == (couponId)) {
                jedis.del("CouponActivity:" + str);
                jedis.srem("CouponActivity:outline", str);
            }
        }
    }


}
