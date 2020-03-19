package com.yh.demo.designpattern.prototype;

import com.yh.business.entity.Person;

import java.util.Arrays;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/18
 */
public class Client {

    public static void main(String[] args) throws CloneNotSupportedException {

        Sheep yh = new Sheep("yh", "007", 12);
        yh.friend = new Person();

        Sheep clone = yh.deepClone();
        Sheep clone1 = yh.deepClone();
        Sheep clone2 = yh.deepClone();
        Sheep clone3 = yh.deepClone();
        Sheep clone4 = yh.deepClone();

        Arrays.asList(clone, clone1, clone2, clone3, clone4).forEach((o) -> {
            System.out.print(o.friend.hashCode() + " ");
            System.out.println(o + "的hashcode：" +o.hashCode());
        });
    }
}
