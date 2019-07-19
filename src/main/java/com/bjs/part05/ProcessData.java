package com.bjs.part05;

import java.util.concurrent.locks.Lock;

/**
 * @Description 锁降级
 * @Author BianJiashuai
 */
public class ProcessData {

    /*
    readLock.lock();
    if (!update) {
        // 必须先释放读锁
        readLock.unlock();
        // 锁降级从写锁获取开始
        writeLock.lock();
        try {
            if (!update) {
                // 准备数据的过程
                update = true;
            }
            readLock.lock();
        } finally {
            writeLock.unlock();
        }
        // 锁降级完成，写锁降级为读锁
    }
    try {
        // 使用数据的过程
        readLock.unlock();
    }
    */
}
