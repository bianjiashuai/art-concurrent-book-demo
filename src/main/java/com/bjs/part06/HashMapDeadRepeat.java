package com.bjs.part06;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Description HashMap 死循环
 * @Author BianJiashuai
 */
public class HashMapDeadRepeat {

    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> map.put(UUID.randomUUID().toString(), ""), "ftf" + i).start();
            }
        }, "ftf");

        t.start();
        t.join();
    }
}
