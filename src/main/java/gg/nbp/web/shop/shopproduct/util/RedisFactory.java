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

//    private int selectIndex=0;

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
//            System.out.println("pool.getMaxIdle(): "+pool.getMaxIdle());
//            System.out.println("pool.getMinIdle(): "+pool.getMinIdle());
//            System.out.println("pool.getNumIdle(): "+pool.getNumIdle());
//            System.out.println("pool.getNumActive(): "+pool.getNumActive());
//            System.out.println("pool.getCreatedCount(): "+pool.getCreatedCount());
//            System.out.println("pool.getMaxTotal(): "+pool.getMaxTotal());

            jedis=pool.getResource();
            jedis.select(selectIndex);

//            try {
//                jedis=pool.getResource();
//                jedis.select(selectIndex);
//            }
//            catch (JedisException e){
//                if("Could not get a resource from the pool".equals(e.getMessage())){
//                    System.out.println("Could not get a resource from the pool.........");
//                }
//            }

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
