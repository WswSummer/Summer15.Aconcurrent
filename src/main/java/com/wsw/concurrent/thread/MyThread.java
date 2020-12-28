package com.wsw.concurrent.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author WangSongWen
 * @Date: Created in 14:28 2020/12/28
 * @Description: ThreadLocal
 */
public class MyThread {
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static final ThreadLocal<String> STRING_THREAD_LOCAL = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return Thread.currentThread().getName();
        }
    };

    private static final ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return Thread.currentThread().getId();
        }
    };

    public void set() {
        STRING_THREAD_LOCAL.set(Thread.currentThread().getName());
        LONG_THREAD_LOCAL.set(Thread.currentThread().getId());
    }

    public Long getLong() {
        return LONG_THREAD_LOCAL.get();
    }

    public String getString() {
        return STRING_THREAD_LOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        //System.out.println(DATE_FORMAT_THREAD_LOCAL.get().format(System.currentTimeMillis()));
        final MyThread myThread = new MyThread();
        myThread.set();
        System.out.println(myThread.getLong());
        System.out.println(myThread.getString());

        Thread thread = new Thread(){
            @Override
            public void run() {
                myThread.set();
                System.out.println(myThread.getLong());
                System.out.println(myThread.getString());
            }
        };
        thread.start();
        thread.join();

        System.out.println(myThread.getLong());
        System.out.println(myThread.getString());
    }
}
