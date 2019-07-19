package com.bjs.part01;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ConcurrencyTest {

    /**
     * 次数越少越能明显感到并发下耗时更长
     * (线程创建 + 上下文切换)
     * 减少上下文切换的方法有无锁并发编程、CAS算法、使用最少线程和使用协程。
     */
    public static final long count = 10000000;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
           for (int i = 0; i < count; i++) {
               a += 5;
           }
        });
        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        thread.join();
        long end = System.currentTimeMillis();
        System.out.println("并发耗时：" + (end - start));
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 5;
        }

        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long end = System.currentTimeMillis();
        System.out.println("串行耗时：" + (end - start));
    }
}
