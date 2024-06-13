package com.jninrain.sunoai.util.Redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/13/16:36
 * @Description:
 */
@Component
public class JedisPoolUtil {
//    @Value("${spring.redis.host}")
//    private static  String HOST;
//    @Value("${spring.redis.port}")
//    private static int PORT;
//    @Value("${spring.redis.lettuce.pool.max-active}")
//    private static int MAX_TOTAL;
//    @Value("${spring.redis.lettuce.pool.max-idle}")
//    private static int MAX_IDEL;
//    @Value("${spring.redis.lettuce.pool.max-wait}")
//    private static int MAX_WAITMILLIS;

    private static final String HOST = "47.106.136.86";
    private static final int PORT = 6379;
    private static final int MAX_TOTAL = 100;
    private static final int MAX_IDEL = 100;
    private static final int MAX_WAITMILLIS = 10 * 1000;
    private static volatile JedisPool jedisPool = null;




    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance()
    {
        if (jedisPool == null)
        {
            synchronized (JedisPoolUtil.class)
            {
                if (jedisPool == null) {

                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(MAX_TOTAL);           // 最大连接数
                    poolConfig.setMaxIdle(MAX_IDEL);              // 最大空闲连接数
                    poolConfig.setMaxWaitMillis(MAX_WAITMILLIS);  // 最大等待时间
                    poolConfig.setTestOnBorrow(true);       // 检查连接可用性, 确保获取的redis实例可用
                    jedisPool = new JedisPool(poolConfig, HOST, PORT,1000,"123456@Panda");
                }
            }
        }

        return jedisPool;
    }

    public static Jedis getJedisInstance() {

        return getJedisPoolInstance().getResource();
    }

    public static void releaseJeids(Jedis jedis) {

        if (jedis != null) {
            jedis.close();// jedisPool.returnResourceObject(jedis)已废弃
        }
    }

    public static void main(String[] args) {
        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = null;
        if(jedisPool == null){
            System.out.println("空的");
        }
        try {
            jedis = jedisPool.getResource();  // 获取Redis连接对象

            // 业务
            jedis.set("key1", "value111");
            System.out.println(jedis.get("key1"));
        } finally {
            jedis.close(); // 关闭redis连接
        }
    }
}
