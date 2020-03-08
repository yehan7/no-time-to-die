package com.yh.demo.jufs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: java.util.function和java, util.stream
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class StreamDemo
{

    public static void main(String[] args)
    {


        //--------------------------java.util.function包的函数式表达-------------------------
        //1.函数型接口:有入参，有回参
        /*Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return 1024;
            }
        };*/
       /* Function<String, Integer> function = s -> {return 1024;};
        System.out.println(function.apply("test"));

        //2.断定型接口:有入参，返回boolean
        Predicate<String> predicate = s -> {return s.isEmpty();};
        System.out.println(predicate.test("test"));

        //3.供给型接口：无入参，有回参
        Supplier<String> supplier = () -> {return "new String";};
        System.out.println(supplier.get());

        //4.消费型接口：有入参，无出餐
        Consumer<String> consumer = s -> {
            System.out.println("输入的是" + s);
        };
        consumer.accept("yh007");*/


        //--------------------------java.util.stream流式计算----------------------------------
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        user1.setId(100001).setUserName("a").setAge(23);
        user2.setId(100002).setUserName("b").setAge(18);
        user3.setId(100003).setUserName("c").setAge(10);
        user4.setId(100004).setUserName("d").setAge(28);
        user5.setId(100005).setUserName("e").setAge(35);
        user6.setId(100006).setUserName("f").setAge(30);

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6);
        //System.out.println(users);
        //比如：这里有一个返回的结果集：
        // 1.找出偶数id并且年级大于24
        // 2.按照年级倒叙
        List<User> collect = users.stream().filter(u -> {
            return u.getAge() % 2 == 0;
        }).filter(u -> {
            return u.getAge() > 24;
        }).sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());

        System.out.println(collect);


    }


}
