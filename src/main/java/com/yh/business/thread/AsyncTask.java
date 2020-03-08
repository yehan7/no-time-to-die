package com.yh.business.thread;

import com.yh.YhAppRunApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask
{
    private static Logger LOGGER = LoggerFactory.getLogger(AsyncTask.class);

    @Async
    public void doTest()
    {
        LOGGER.info("当前是线程：" + Thread.currentThread().getName());
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
