package com.wsw.concurrent.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author WangSongWen
 * @Date: Created in 10:11 2020/12/25
 * @Description:
 */
@Configuration
@EnableAsync  // 异步
public class ScheduleThreadPool {
    @Bean
    public Executor executor1() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5),
                new ThreadPoolExecutor.AbortPolicy()  // 拒绝策略
        );
        return threadPool;
    }

    @Bean
    public Executor executor2() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5),
                new ThreadPoolExecutor.AbortPolicy()  // 拒绝策略
        );
        return threadPool;
    }
}
