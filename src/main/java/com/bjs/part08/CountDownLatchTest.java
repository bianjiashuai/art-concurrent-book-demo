package com.bjs.part08;

import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @Author BianJiashuai
 */
public class CountDownLatchTest {
    private static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("第1个线程");
            latch.countDown();
        }).start();
        new Thread(() -> {
            System.out.println("第2个线程");
            latch.countDown();
        }).start();
        latch.await();
        System.out.println("主方法");
    }
}
