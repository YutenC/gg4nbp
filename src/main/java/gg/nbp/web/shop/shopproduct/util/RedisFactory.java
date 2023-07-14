package gg.nbp.web.shop.shopproduct.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.List;

public class RedisFactory  {

    private List<RedisContent> registerRedisService = new ArrayList<>();
    private static ThreadLocal<RedisFactory> threadLocal = new ThreadLocal<>();
    private Jedis jedis=null;


    public int process() {
        if(registerRedisService!=null){
            for(RedisContent r:registerRedisService){
                r.run();
            }
        }
        return 1;
    }

    public static RedisFactory getRedisServiceInstance() {
        RedisFactory redisServiceImpl = threadLocal.get();

        if (redisServiceImpl == null) {
            redisServiceImpl = new RedisFactory();
            threadLocal.set(redisServiceImpl);
        }
        return redisServiceImpl;
    }

    public Jedis getJedis(int selectIndex) throws JedisException{
        if (jedis==null) {
            JedisPool pool= JedisUtil.getJedisPool();
            jedis=pool.getResource();
            jedis.select(selectIndex);
        }

        return jedis;
    }

    public void registerRedisService(RedisContent redisService){
        registerRedisService.add(redisService);
    }

    public static void clear(){
        RedisFactory redisServiceImpl = threadLocal.get();
        if (redisServiceImpl != null) {
            redisServiceImpl.closeJedis();
            redisServiceImpl.clearRedisService();
            threadLocal.set(null);
        }
    }


    public void clearRedisService(){
        if(registerRedisService!=null){
            registerRedisService.clear();
        }
    }

    public void closeJedis(){
        if(jedis!=null && jedis.isConnected()){
            jedis.close();
        }
    }

    public static void shutdownRedis() {
        JedisUtil.shutdownJedisPool();
    }

}
