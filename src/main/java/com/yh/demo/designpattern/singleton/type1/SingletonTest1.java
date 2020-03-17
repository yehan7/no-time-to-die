package com.yh.demo.designpattern.singleton.type1;

/**
 * @Description: 饿汉式单例模式
 * @Since: YH007
 * @Date: 2020/3/16
 */
public class SingletonTest1 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "创建了一个实例：" +instance);
            }, "线程"+ i).start();
        }
    }
}


class Singleton {

    //1.构造方法私有化
    private Singleton() {
    }

    //2.本类内部创建对象实例
    private static final Singleton instance = new Singleton();


    /*static { // 在静态代码块中，创建单例对象
        instance = new Singleton();
    }*/

    //3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return instance;
    }


}
