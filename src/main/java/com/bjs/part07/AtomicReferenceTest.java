package com.bjs.part07;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author BianJiashuai
 */
public class AtomicReferenceTest {

    private static final AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("张三", 18);
        atomicUserRef.set(user);
        User updateUser = new User("李四", 19);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get());
    }


    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        private int age;
    }
}
