package com.yh.demo.designpattern.singleton.type6;

/**
 * @Description: 单例模式 枚举
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest6 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.INSTANCE;
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" + instance);
            }, "线程" + i).start();
        }
    }

}


//使用枚举，可以实现单例, 推荐
enum Singleton {
    INSTANCE; //属性
    public void sayOK() {
        System.out.println("ok~");
    }
}