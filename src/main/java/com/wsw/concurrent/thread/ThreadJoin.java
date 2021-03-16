package com.wsw.concurrent.thread;

/**
 * @Author WangSongWen
 * @Date: Created in 9:47 2021/3/16
 * @Description:
 */
public class ThreadJoin {
    private class A extends Thread {
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    private class B extends Thread {
        private final A a;

        B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        }
    }

    public void test() {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();
    }

    public static void main(String[] args) {
        new ThreadJoin().test();
    }

}
