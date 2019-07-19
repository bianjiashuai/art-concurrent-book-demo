package com.bjs.part08;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author BianJiashuai
 */
public class ExchangeTest {

    private static Exchanger<String> ex = new Exchanger<>();
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        executor.execute(() -> {
            try {
                String A = "银行流水A";
                ex.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                String B = "银行流水B";
                String A = ex.exchange(B);
                System.out.println("A与B的数据是否一致: " + A.equals(B));
                System.out.println("A的数据是: " + A);
                System.out.println("B的数据是: " + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }
}
