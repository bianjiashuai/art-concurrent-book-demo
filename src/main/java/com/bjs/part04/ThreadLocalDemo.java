package com.bjs.part04;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ThreadLocalDemo {

    public static final ThreadLocal<Long> TIME_THREAD_LOCAL = ThreadLocal.withInitial(() -> System.currentTimeMillis());

    public static final void begin() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
    }

    public static void main(String[] args) {
        begin();
        SleepUtil.second(1);
        System.out.println("Cost: " + end() + " mills");
    }
}
