package com.yh.demo.juc.aync;

/**
 * @Description: synchronized抢票
 * @Since: YH007
 * @Date: 2020/3/6
 */
public class TakeTicketsDemo {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                ticket.take();
            }).start();
        }
    }

}


class Ticket {

    private int num = 100;


    public synchronized void take(){

        while (num == 0) {
            System.out.println("没票了");
            return;
        }
        System.out.println(Thread.currentThread().getName() + "抢到了第" + num + "张票！");
        num --;


    }

}