package com.bjs.part07;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description
 * @Author BianJiashuai
 */
public class AtomicIntegerFieldUpdaterTest {

    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        User zs = new User("张三", 20);
        System.out.println(a.getAndIncrement(zs));
        System.out.println(a.get(zs));
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        public volatile int age;
    }

}
