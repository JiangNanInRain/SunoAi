package com.jninrain.sunoai;

import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/10:28
 * @Description:
 */
public class test {
    public static void main(String[] args) {
//        Jedis jedis = new Jedis("47.106.136.86",6379);
//        jedis.auth("123456@Panda");
//        System.out.println(jedis.ping());
        String topic  = "lnsendout/yqhby2024/NA300AI(2N-NB01)/864901066014584/rtdata/h.1.0.0|s.1.0.0";
        if(topic.matches("^lnsendout/yqhby2024/.+/.+/rtdata/.+$")){
            String[] topic_Array = topic.split("/");
            System.out.println("设备型号:"+topic_Array[2]+"  设备编号:"+topic_Array[3]);
        }
    }
}
