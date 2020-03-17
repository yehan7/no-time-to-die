package com.yh.demo.designpattern.singleton.type4;

/**
 * @Description: 懒汉式 双重检查 线程安全
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest4 {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" + instance);
            }, "线程" + i).start();
        }
    }

}


// 懒汉式(线程安全，同步方法)
class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
    }

    //提供一个静态的公有方法，加入双重检查代码，解决线程安全问题, 同时解决懒加载问题
    //同时保证了效率, 推荐使用

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
