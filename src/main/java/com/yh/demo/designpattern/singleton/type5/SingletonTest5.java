package com.yh.demo.designpattern.singleton.type5;

/**
 * @Description: 单例模式 静态内部类
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest5 {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" + instance);
            }, "线程" + i).start();
        }
    }
}


// 静态内部类完成， 推荐使用
class Singleton {
    private static volatile Singleton instance;

    //构造器私有化
    private Singleton() {
    }

    //写一个静态内部类,该类中有一个静态属性 Singleton
    private static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    //提供一个静态的公有方法，直接返回SingletonInstance.INSTANCE

    public static synchronized Singleton getInstance() {

        return SingletonInstance.INSTANCE;
    }
}
