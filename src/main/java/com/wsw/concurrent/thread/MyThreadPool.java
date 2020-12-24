package com.wsw.concurrent.thread;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author WangSongWen
 * @Date: Created in 10:38 2020/12/24
 * @Description: 线程池
 */
public class MyThreadPool {
    // FixedThreadPool---创建一个固定大小的线程池，可控制并发的线程数，超出的线程会在队列中等待
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

        for (int i = 0; i < 4; i++) {
            threadPool.submit(() -> {
                System.out.println("任务被执行,线程: " + Thread.currentThread().getName());
            });
        }
    }

    // CachedThreadPool---创建一个可缓存的线程池，若线程数超过处理所需，缓存一段时间后会回收，若线程数不够，则新建线程
    public static void cachedThreadPool() {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println("任务被执行,线程: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // SingleThreadExecutor---创建单个线程数的线程池，它可以保证先进先出的执行顺序
    public static void singleThreadExecutor() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPool.execute(() -> {
                System.out.println(index + " 任务被执行");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // ScheduledThreadPool---创建一个可以执行延迟任务的线程池
    public static void scheduledThreadPool() {
//        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
//
//        System.out.println("添加任务,时间: " + new Date());
//        threadPool.schedule(() -> {
//            System.out.println("任务被执行,时间: " + new Date());
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//        });
    }

    public static void main(String[] args) {
        //MyThreadPool.fixedThreadPool();
        //MyThreadPool.cachedThreadPool();
        MyThreadPool.singleThreadExecutor();
    }
}
