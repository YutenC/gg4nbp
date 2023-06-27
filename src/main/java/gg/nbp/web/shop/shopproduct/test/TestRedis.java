package gg.nbp.web.shop.shopproduct.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.ListDirection;

import java.util.List;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        if (jedis.exists("customers"))
            jedis.del("customers");

        jedis.lpush("customers", "David", "James", "Vincent", "Ben", "Ron", "George", "Howard");
        // List內容："Howard", "George", "Ron", "Ben", "Vincent", "James", "David"
        System.out.println(jedis.lpop("customers"));
        // List內容："George", "Ron", "Ben", "Vincent", "James", "David"
        jedis.rpush("customers", "Jerry", "Joe", "Smith");
        // List內容："George", "Ron", "Ben", "Vincent", "James", "David", "Jerry", "Joe", "Smith"

        System.out.println("============================");

        List<String> range1 = jedis.lrange("customers", 3, 6);
        for (String customer : range1)
            System.out.println(customer);

        System.out.println("共有" + jedis.llen("customers") + "位客戶");

        System.out.println("============================");

//		jedis.ltrim("customers", 1, -2);


          jedis.lmpop(ListDirection.RIGHT,  1,"customers");
//        List<String> values= keyValues.getValue();
//        jedis.rpush("customers", (String[]) values.toArray());

//		jedis.ltrim("customers", 3, 6);
        List<String> range2 = jedis.lrange("customers", 0, jedis.llen("customers") - 1);
//		List<String> range2 = jedis.lrange("customers", 0, - 1);
    }
}
