package com.bjs.part04;

/**
 * @Description
 * @Author BianJiashuai
 */
public class JoinTest {

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有前一个线程的引用，需要等前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Join(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        SleepUtil.second(3);
        System.out.println(Thread.currentThread().getName() + " terminate. @" + DateUtil.getFormatDate());
    }

    static class Join implements Runnable {
        private Thread thread;
        public Join(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
                SleepUtil.second(1);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate. @" + DateUtil.getFormatDate());
        }
    }
}
