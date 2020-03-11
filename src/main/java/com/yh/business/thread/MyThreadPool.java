package com.yh.business.thread;

import java.util.concurrent.BlockingQueue;

/**
 * Created by idea China
 * Author: YH007
 * Time: 13:14 2020/2/9
 * Description:
 */
public class MyThreadPool {
    BlockingQueue<Runnable> workQueue;
    //List<WorkThread> workThreadList = new ArrayList<>();

    public MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkThread workThread = new WorkThread();
            workThread.start();
            workQueue.add(workThread);
        }
    }

    public void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }


    class WorkThread extends Thread {
        public void run() {
            while (true) {
                Runnable task = null;
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }
        }


    }
}
