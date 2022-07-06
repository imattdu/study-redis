package com.matt.study.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
jedis cluster
 */
public class JedisClusterTest {

    public static void main(String[] args) throws IOException {

        Set<HostAndPort> set = new HashSet<HostAndPort>();
        // 去中心化 任一一个节点均可以访问
        set.add(new HostAndPort("127.0.0.1", 6381));
        set.add(new HostAndPort("127.0.0.1", 6379));
        set.add(new HostAndPort("127.0.0.1", 6380));
        set.add(new HostAndPort("127.0.0.1", 6389));
        set.add(new HostAndPort("127.0.0.1", 6390));
        set.add(new HostAndPort("127.0.0.1", 6391));

        JedisCluster jedisCluster = new JedisCluster(set, 5000, 1000);
        jedisCluster.set("m1", "m1");
        String res = jedisCluster.get("m1");
        System.out.println(res);
        jedisCluster.close();
    }


}
