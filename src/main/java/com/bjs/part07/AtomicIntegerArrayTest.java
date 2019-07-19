package com.bjs.part07;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Description
 * @Author BianJiashuai
 */
public class AtomicIntegerArrayTest {
    private static int[] val = new int[]{1, 2, 3};
    /**
     * ⭐⭐⭐⭐⭐⭐特别注意⭐⭐⭐⭐⭐⭐
     * 是复制一份传递，并不是引用传递
     */
    private static final AtomicIntegerArray aia = new AtomicIntegerArray(val);

    public static void main(String[] args) {
        System.out.println(aia.getAndSet(0, 3));
        System.out.println(aia.get(0));
        System.out.println(val[0]);
    }
}
