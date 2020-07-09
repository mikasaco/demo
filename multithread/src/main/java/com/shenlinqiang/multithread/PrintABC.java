package com.shenlinqiang.multithread;

/**
 * 三个线程循环打印a,b,c10次
 * 输出：abcabcabcabcabcabcabcabcabcabc
 * 用volatile解决
 */
public class PrintABC {

    public static void main(String[] args) {
        new Thread(new MyRunnable(0, 'a')).start();
        new Thread(new MyRunnable(1, 'b')).start();
        new Thread(new MyRunnable(2, 'c')).start();
    }

    static class MyRunnable implements Runnable {

        static  int status = 0;

        private int num;

        private char c;

        public MyRunnable(int num, char c) {
            this.num = num;
            this.c = c;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; ) {
                while (status % 3 == this.num) {
                    System.out.print(c);
                    status++;
                    i++;
                }
            }

        }


    }
}
