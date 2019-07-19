package com.bjs.part07;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author BianJiashuai
 */
public class AtomicIntegerTest {
    private static final AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai);
    }
}
