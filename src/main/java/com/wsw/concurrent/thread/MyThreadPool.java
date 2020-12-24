package com.wsw.concurrent.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author WangSongWen
 * @Date: Created in 10:38 2020/12/24
 * @Description: 线程池
 */
public class MyThreadPool {
    // FixedThreadPool---创建一个固定大小的线程池，可控制并发的线程数，超出的线程会在队列中等待。
    public static void fixedThreadPool() {
        // 创建 2 个数据级的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
//        // 创建任务
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("任务被执行,线程: " + Thread.currentThread().getName());
//            }
//        };
//        // 线程池执行任务(一次添加 4 个任务)
//        // 执行任务的方法有两种:submit 和 execute
//        threadPool.submit(runnable);
//        threadPool.execute(runnable);
//        threadPool.execute(runnable);
//        threadPool.execute(runnable);
        threadPool.submit(() -> {
            System.out.println("任务被执行,线程: " + Thread.currentThread().getName());
        });

        threadPool.execute(() -> {
            System.out.println("任务被执行,线程: " + Thread.currentThread().getName());
        });
    }
}
