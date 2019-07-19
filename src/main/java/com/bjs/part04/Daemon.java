package com.bjs.part04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * @Description
 * @Author BianJiashuai
 */
public class Daemon {

    public static void main(String[] args) throws FileNotFoundException {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {

        @Override
        public void run() {
            try {
                SleepUtil.second(10);
            } finally {
                System.out.println("Thread DaemonRunner finally run.");
            }
        }
    }
}
