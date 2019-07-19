package com.bjs.part03;

/**
 * @Description
 * @Author BianJiashuai
 */
public class MonitorExample {

    int a = 0;

    public synchronized void write() {
        a++;
    }

    public synchronized void read() {
        int i = a;

    }
}
