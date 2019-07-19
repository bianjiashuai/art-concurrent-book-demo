package com.bjs.part03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ReenTrantLockExample {

    int a = 0;

    ReentrantLock lock = new ReentrantLock();

    public void write() {
        lock.lock();
        try {
            a++;
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            int i = a;
            // 后续操作
        } finally {
            lock.unlock();
        }
    }
}
