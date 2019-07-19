package com.bjs.part04;

/**
 * @Description
 * @Author BianJiashuai
 */
public class Deprecated {

    public static void main(String[] args) {
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();

        SleepUtil.second(3);
        // 将 PrintThread 暂停
        printThread.suspend();
        System.out.println("Main Thread suspend PrintThread at " + DateUtil.getFormatDate());

        SleepUtil.second(3);
        // 将 PrintThread 恢复
        printThread.resume();
        System.out.println("Main Thread resume PrintThread at " + DateUtil.getFormatDate());

        SleepUtil.second(3);
        // 将 PrintThread 停止
        printThread.stop();
        System.out.println("Main Thread stop PrintThread at " + DateUtil.getFormatDate());

        SleepUtil.second(3);
    }

    static class Runner implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "run at " + DateUtil.getFormatDate());
                SleepUtil.second(1);
            }
        }
    }


}
