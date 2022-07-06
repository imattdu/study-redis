package com.matt.study.jedis;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Set;

public class JedisDemo {

    public static void main(String[] args) {

        // 192.168.199.222
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }

    private Jedis jedis = null;

    @Before
    public void init() {
        // 192.168.199.222
        // jedis有问题
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Test
    public void testKey() {
        //Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");
        Set<String> keys = jedis.keys("*");
        System.out.println("key cnt:" + keys.size());
        System.out.println("key-------");
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println("-------");
        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.ttl("k1"));
        System.out.println(jedis.get("k1"));

    }

    @Test
    public void testString() {
        jedis.mset("s1","1","s2","2","s3","3");
        System.out.println(jedis.mget("s1","s2","s3"));
    }

    @Test
    public void testVerify() {

        // 1.判断验证码的次数 cnt: userId ，满足则存入redis ttl:userId+code
        // 2. 验证码判断

        Random random = new Random();
        int verificationCode = random.nextInt(999999);
        System.out.println(verificationCode);

        verificationCode = 123456;

        String userId = "3";
        Long cnt = jedis.incr("phone:cnt:" + userId);
        if (cnt > 3) {
            System.out.println("请明天再来");
            return;
        }
        if (cnt == 1) {
            String res = jedis.setex("phone:ttl:" + userId + ":" +verificationCode, 2, "1");
            System.out.println(res);
        } else {
            Long ttl = jedis.ttl("phone:ttl:" + userId + ":" +verificationCode);
            System.out.println(ttl);
        }

    }



}
