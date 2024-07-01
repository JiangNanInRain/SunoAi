package com.jninrain.sunoai;

import io.swagger.models.auth.In;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Stream;

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
//        String topic  = "lnsendout/yqhby2024/NA300AI(2N-NB01)/864901066014584/rtdata/h.1.0.0|s.1.0.0";
//        if(topic.matches("^lnsendout/yqhby2024/.+/.+/rtdata/.+$")){
//            String[] topic_Array = topic.split("/");
//            System.out.println("设备型号:"+topic_Array[2]+"  设备编号:"+topic_Array[3]);
//        }
//
//        List<String> list = new ArrayList<>();
//        list.add("123");
//        list.add("567");
//        list.add("8910");
//        list.stream().filter(str->str.startsWith("1")).forEach(str-> System.out.println(str));
//
//
//        //并行流
//        list.parallelStream();
//
//
//        //数组流
//        Arrays.stream(new int[]{1,2,3,4}).filter(num->num>2).forEach(System.out::println);
//
//
//        Stream<Integer> stream2 = Stream.of();
//        Stream<Integer> stream3 = Stream.iterate(0,(x)->x+3).limit(4);
//        stream3.forEach(System.out::println);
//
//        System.out.println("________________________________");
//        Stream<Double> stream4 = Stream.generate(Math::random).limit(3);
//        stream4.forEach(System.out::println);
//        System.out.println("________________________________");
//
//        List<Integer> list2 = Arrays.asList(1, 3, 6, 9,7,8, 12, 4);
//        Stream<Integer> findFirst = list2.stream().parallel().filter(x->x>6);
//         findFirst.forEach(System.out::println);
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);

        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max( ((o1, o2) -> o1.compareTo(o2))
        );
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());


    }
}
