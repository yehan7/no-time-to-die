package com.yh.demo.designpattern.singleton.type3;

/**
 * @Description: 懒汉式(线程安全，同步方法)
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest3 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" + instance);
            }, "线程" + i).start();
        }
    }



}


// 懒汉式(线程安全，同步方法)
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    //提供一个静态的公有方法，加入同步处理的代码，解决线程安全问题
    //即懒汉式
    public static synchronized Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}