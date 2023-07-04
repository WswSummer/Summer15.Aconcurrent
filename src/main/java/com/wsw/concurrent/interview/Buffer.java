package com.wsw.concurrent.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: wangsongwen
 * @Date: 2023/7/4 23:49
 */
class Buffer {
    private final int capacity;
    private final List<Integer> buffer;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new ArrayList<>();
    }

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == capacity) {
            // 缓冲区已满，等待消费者消费
            wait();
        }
        buffer.add(value);
        System.out.println("生产者生产：" + value);
        notifyAll(); // 唤醒所有等待的线程
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            // 缓冲区为空，等待生产者生产
            wait();
        }
        int value = buffer.remove(0);
        System.out.println("消费者消费：" + value);
        notifyAll(); // 唤醒所有等待的线程
    }
}

class Producer implements Runnable {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                buffer.produce(i);
                Thread.sleep(100); // 模拟生产耗时
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                buffer.consume();
                Thread.sleep(200); // 模拟消费耗时
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ProducerConsumerExample {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        Thread producerThread1 = new Thread(producer);
        Thread producerThread2 = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread1.start();
        producerThread2.start();
        consumerThread.start();
    }
}
