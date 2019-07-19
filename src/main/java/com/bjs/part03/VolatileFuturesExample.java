package com.bjs.part03;

/**
 * @Description
 * @Author BianJiashuai
 */
public class VolatileFuturesExample {
    volatile long v1 = 0;

    public void setV1(long v1) {
        this.v1 = v1;
    }

    public void getAndIncrement() {
        v1++;
    }

    public long getV1() {
        return v1;
    }

    //-------------多个线程分别调用上面程序的3个方法，这个程序在语义上和下面程序等价--------------------

    long v2 = 0;

    public synchronized void setV2(long v2) {
        this.v2 = v2;
    }

    public void getAndIncrement2() {
        long v2Tmp = getV2();
        v2Tmp += 1L;
        setV2(v2Tmp);
    }

    public synchronized long getV2() {
        return v2;
    }
}
