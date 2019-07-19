package com.bjs.part05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 有界队列
 * @Author BianJiashuai
 */
public class BoundedQueue<T> {
    private Object[] items;
    private int addIndex, removeIndex, count;   // 添加的下标，删除的下标以及数组中元素的个数
    Lock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    // 队列追加，如果到尾部并且仍有空位下一次从头开始
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            // 使用while防止过早通知，只有当条件满足的时候才推出循环
            while (count == items.length) {
                // 队列已满, notFull 需要暂停，否则会认为没有满
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                // 到达尾部，重置索引
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();  // 唤醒非空的标志
        } finally {
            lock.unlock();
        }
    }

    // 从头开始删除元素
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                // 队列为空，notEmpty 需要暂停，否则会认为队列仍有内容
                notEmpty.await();
            }
            Object x = items[removeIndex];
            if (++removeIndex == items.length) {
                // 到达尾部，重置索引
                removeIndex = 0;
            }
            --count;
            notFull.signal();   // 唤醒非满的标志
            return (T) x;
        } finally {
            lock.unlock();
        }
    }
}
