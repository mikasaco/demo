package com.shenlinqiang.multithread;

import java.util.concurrent.Semaphore;

/**
 * 三个线程循环打印a,b,c10次
 * 输出：abcabcabcabcabcabcabcabcabcabc
 * 这里用信号量解决
 */
public class PrintABC01 {

    public static void main(String[] args) {
        new Thread(new ARunnable()).start();
        new Thread(new BRunnable()).start();
        new Thread(new CRunnable()).start();
    }

    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(0);
    private static Semaphore s3 = new Semaphore(0);

    static class ARunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10;i++ ) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print('a');
                s2.release();
            }

        }
    }

    static class BRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10;i++ ) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print('b');
                s3.release();
            }
        }
    }

    static class CRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10;i++ ) {
                try {
                    s3.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print('c');
                s1.release();
            }
        }
    }
}
