package com.wsw.concurrent.task;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author WangSongWen
 * @Date: Created in 13:17 2020/12/25
 * @Description: 自定义线程池
 */
public class MyTask implements Task {

    @Override
    public void execute() {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        int keepAliveTime = 100;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        ThreadFactory threadFactory = new MyThreadFactory();
        RejectedExecutionHandler handler = new MyIgnorePolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);

        executor.prestartAllCoreThreads();  // 预启动所有核心线程

        for (int i = 1; i <= 10; i++) {
            final String taskId = String.valueOf(i);
            executor.execute(() -> {
                System.out.println("Task-" + taskId + " is running... ThreadName: " + Thread.currentThread().getName() + " " + new Date());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }

    private static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger num = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "my-thread-" + num.getAndIncrement());
            System.out.println(thread.getName() + " has been created.");
            return thread;
        }
    }

    private static class MyIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            doLog(r, executor);
        }

        private void doLog(Runnable r, ThreadPoolExecutor executor) {
            // 日志记录
            System.out.println(r.toString() + " rejected.");
        }
    }

    public static void main(String[] args) {
        // 当前设备的CPU个数
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
        MyTask task = new MyTask();
        task.execute();
    }
    /*
        1，由于线程预启动，首先创建了1，2号线程，然后task1，task2被执行
        2，但任务提交没有结束，此时任务task3，task6到达发现核心线程已经满了，进入等待队列；
        3，等待队列满后创建任务线程3，4执行任务task3，task6，同时task4，task5进入队列；
        4，此时创建线程数（4）等于最大线程数，且队列已满，所以7，8，9，10任务被拒绝；
        5，任务执行完毕后回头来执行task4，task5，队列清空。
     */
}
