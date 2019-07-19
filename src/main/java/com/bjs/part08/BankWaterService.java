package com.bjs.part08;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description
 * @Author BianJiashuai
 */
public class BankWaterService implements Runnable {

    // 创建4个屏障
    CyclicBarrier cb = new CyclicBarrier(4, this);
    // 启动4个线程
    Executor executor = Executors.newFixedThreadPool(4);

    //保存每个sheet计算出的银行流水结果
    ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        int res = 0;
        for (Map.Entry<String, Integer> entry : sheetBankWaterCount.entrySet()) {
            res += entry.getValue();
        }
        sheetBankWaterCount.put("res", res);
        System.out.println(res);
    }

    public static void main(String[] args) {
        new BankWaterService().count();
    }
}
