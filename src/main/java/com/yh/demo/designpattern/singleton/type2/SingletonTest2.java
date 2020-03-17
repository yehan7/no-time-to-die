package com.yh.demo.designpattern.singleton.type2;

/**
 * @Description: 懒汉式单例模式(线程不安全)
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest2 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" + instance);
            }, "线程" + i).start();
        }
    }
}

class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    //提供一个静态的公有方法，当使用到该方法时，才去创建 instance
    //即懒汉式
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


}
