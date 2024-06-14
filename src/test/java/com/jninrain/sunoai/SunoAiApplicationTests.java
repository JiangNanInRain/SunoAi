package com.jninrain.sunoai;

import com.jninrain.sunoai.util.Redis.JedisPoolUtil;
import com.jninrain.sunoai.util.Redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@SpringBootTest
class SunoAiApplicationTests {

    @Resource
    private  RedisUtil redisUtil;
    JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();



    @Test
    public void test2() {
        redisUtil.set("名字","王一哲");
        System.out.println(redisUtil.get("名字"));
        String[] songIdList;

    }

}



