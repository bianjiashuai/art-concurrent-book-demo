package com.bjs.part04;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ShutDown {

    public static void main(String[] args) {
        Thread countThread = new Thread(new Runner(), "CountThread");
        countThread.start();
        // 休眠1秒，main线程对countThread线程中断，使countThread能感知到中断而结束
        SleepUtil.second(1);
        countThread.interrupt();

        Runner runner = new Runner();
        countThread = new Thread(runner, "CountThread");
        countThread.start();
        // 休眠1秒, main线程对runner进行取消, 使CountThread能感知到on为false而结束
        SleepUtil.second(1);
        runner.cancel();
    }

    static class Runner implements Runnable {
        private long i;
        private volatile boolean on  = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
