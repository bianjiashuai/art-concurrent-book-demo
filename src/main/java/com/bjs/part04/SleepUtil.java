package com.bjs.part04;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author BianJiashuai
 */
public class SleepUtil {

    public static final void second(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException ex) {

        }
    }
}
