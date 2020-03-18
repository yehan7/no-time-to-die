package com.yh.demo.designpattern.prototype;

import com.yh.business.entity.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * @Description: 原型模式： 1.重写clone方法 2.序列化创建克隆对象
 * @Since: YH007
 * @Date: 2020/3/18
 */
public class Sheep implements Serializable, Cloneable {

    private String name;
    private String type;
    private int age;
    public Person friend;

    //重写克隆方法
    @Override
    protected Sheep clone() throws CloneNotSupportedException {
        Sheep clone = (Sheep) super.clone();
        clone.friend = (Person) friend.clone();
        return clone;
    }

    /**
     * 深度克隆对象
     *
     * @return Sheep
     */
    public Sheep deepClone() {
        //创建流对象
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return (Sheep) o;
        } catch (Exception e) {

            return null;

        } finally {
            try {
                ois.close();
                bis.close();
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sheep(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", friend=" + friend +
                '}';
    }

}
