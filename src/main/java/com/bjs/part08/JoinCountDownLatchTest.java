package com.bjs.part08;

/**
 * @Description
 * @Author BianJiashuai
 */
public class JoinCountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("parse1 finish"));
        Thread t2 = new Thread(() -> System.out.println("parse2 finish"));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("all finish");
    }
}
