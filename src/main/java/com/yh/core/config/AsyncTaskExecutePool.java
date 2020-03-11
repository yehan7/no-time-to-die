package com.yh.core.config;

import com.yh.YhAppRunApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncTaskExecutePool implements AsyncConfigurer {

    private static Logger LOGGER = LoggerFactory.getLogger(AsyncTaskExecutePool.class);


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
        poolExecutor.setCorePoolSize(4);
        poolExecutor.setMaxPoolSize(8);
        poolExecutor.setQueueCapacity(10);
        poolExecutor.setKeepAliveSeconds(60);
        poolExecutor.setThreadNamePrefix("yhExecutor-");
        poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        poolExecutor.initialize();

        return poolExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return ((throwable, method, objects) ->
        {
            LOGGER.error(throwable.getMessage());
        });
    }
}
