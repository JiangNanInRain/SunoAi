package com.jninrain.sunoai;

import com.jninrain.sunoai.util.Redis.JedisPoolUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class SunoAiApplicationTests {


    JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();



    @Test
    public void test2() {

    }

}



