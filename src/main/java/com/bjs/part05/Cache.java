package com.bjs.part05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description
 * @Author BianJiashuai
 */
public class Cache {

    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock rl = rwl.readLock();
    static Lock wl = rwl.writeLock();

    // 读取一个key的value
    public static final Object get(String key) {
        rl.lock();
        try {
            return map.get(key);
        } finally {
            rl.unlock();
        }
    }

    // 设置key对应的value，并返回旧的value
    public static final Object put(String key, Object value) {
        wl.lock();
        try {
            return map.put(key, value);
        } finally {
            wl.unlock();
        }
    }

    // 清空所有内容
    public static final void clear() {
        wl.lock();
        try {
            map.clear();
        } finally {
            wl.unlock();
        }
    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-12));
        System.out.println(Integer.toBinaryString(-12 >> 3));
        System.out.println(Integer.toBinaryString(1 + (1 << 16)));
    }
}
