package com.wsw.concurrent.interview;

import java.util.LinkedList;

/**
 * @Description: java编程：实现一个生产者消费者模式（自己实现，不要使用JDK自带的阻塞队列）
 * 要求：a. 多个生产者、一个消费者，在保证逻辑正确的前提下，尽可能提高生产和消费的性能
 * b. 代码清晰、逻辑完整
 * c. 线程安全
 * @Author: wangsongwen
 * @Date: 2023/7/4 20:59
 */
public class ProducerConsumer {
    // 自定义的缓冲区来充当生产者和消费者之间的中介
    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final int BUFFER_SIZE = 10;

    // 生产者方法不断地生成随机数，并将其添加到缓冲区中
    public void produce() throws InterruptedException {
        // 使用synchronized来确保在修改缓冲区时只有一个线程执行操作
        while (true) {
            synchronized (this) {
                // 当缓冲区已满时，生产者线程将等待，直到消费者线程从缓冲区中消费数据
                while (buffer.size() == BUFFER_SIZE) {
                    // 使用 wait() 和 notifyAll() 来实现线程间的通信
                    wait();
                }
                int item = (int) (Math.random() * 100);
                buffer.add(item);
                System.out.println("Produced: " + item);
                notifyAll();
            }
            Thread.sleep(100);
        }
    }

    // 消费者方法从缓冲区中取出数据进行消费
    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                // 当缓冲区为空时，消费者线程将等待，直到生产者线程向缓冲区中添加数据
                while (buffer.isEmpty()) {
                    wait();
                }
                int item = buffer.removeFirst();
                System.out.println("Consumed: " + item);
                notifyAll();
            }
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Thread producerThread1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread producerThread2 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread1.start();
        producerThread2.start();
        consumerThread.start();
    }
}