package com.bjs.part04.connection;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ConnectionPoolTest {
    static ConnectionPool pool = new ConnectionPool(10);

    // 保证所有ConnectionRunner都能同时开始
    static CountDownLatch start = new CountDownLatch(1);

    // main 线程会等待所有ConnectionRunner结束后才能继续进行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量，可以修改来进行观察
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++ ) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread" + i);
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("notGot connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
            }

            while (count > 0) {
                try {
                    // 从线程池获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
