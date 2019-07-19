package com.bjs.part04;

import java.util.concurrent.TimeUnit;

/**
 * Thread[WaitThread,5,main]flag is true, wait@08:59:26
 * Thread[NotifyThread,5,main]hold lock, notify@08:59:27
 * Thread[NotifyThread,5,main]hold lock again, sleep@08:59:32
 * Thread[WaitThread,5,main]flag is false, running@08:59:37
 * 第三行和第四行输出的顺序可能会出现互换
 * @Description
 * @Author BianJiashuai
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();

        SleepUtil.second(1);

        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            // 加锁，拥有lock的monitor
            synchronized (lock) {
                // 当条件不满足的时候，继续wait，同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is true, wait@" + DateUtil.getFormatDate());
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + "flag is false, running@" + DateUtil.getFormatDate());
                }
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            // 加锁，拥有lock的monitor
            synchronized (lock) {
                // 获得lock锁，然后进行通知，通知不会释放lock锁，知道当前线程释放lock锁，其他线程才能继续获得使用
                System.out.println(Thread.currentThread() + "hold lock, notify@" + DateUtil.getFormatDate());
                lock.notifyAll();
                flag = false;
                SleepUtil.second(5);
            }

            // 在此加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again, sleep@" + DateUtil.getFormatDate());
                SleepUtil.second(5);
            }
        }
    }
}
