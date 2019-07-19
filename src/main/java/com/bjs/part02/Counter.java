package com.bjs.part02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author BianJiashuai
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        Counter counter = new Counter();
        List<Thread> threads = new ArrayList<>(100);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
               for (int j = 0; j < 10000; j++) {
                   counter.safeCount();
                   counter.count();
               }
            });

            threads.add(thread);
        }

        // 启动所有线程
        for (Thread t : threads) {
            t.start();
        }

        // 等待所有线程结束
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(counter.i);
        System.out.println(counter.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用CAS实现线程安全计数
     */
    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean success = atomicI.compareAndSet(i, 1);
            if (success) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数
     */
    private void count() {
        i++;
    }
}
