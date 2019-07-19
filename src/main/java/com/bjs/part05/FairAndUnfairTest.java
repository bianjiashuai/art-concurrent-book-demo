package com.bjs.part05;

import com.bjs.part04.SleepUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author BianJiashuai
 */
public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock("公平锁", fairLock);
    }

    @Test
    public void unfair() {
        testLock("非公平锁", unfairLock);
    }

    private void testLock(String type, Lock lock) {
        System.out.println(type);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(lock)) {
                @Override
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }

        SleepUtil.second(11);
    }

    private static class Job extends Thread {
        private Lock lock;
        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    SleepUtil.second(1);
                    System.out.println("获取锁的当前线程:[" + Thread.currentThread().getName()
                            + "], 同步队列中线程:[" + ((ReentrantLock2)lock).getQueuedThreads() + "]");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> threads = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(threads);
            return threads;
        }
    }
}
