package com.bjs.part08;

import com.bjs.part04.SleepUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description
 * @Author BianJiashuai
 */
public class CyclicBarrierTest2 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        /**
         * 构造函数CyclicBarrier（int parties，Runnable barrierAction），
         * 用于在线程到达屏障时，优先执行barrierAction，方便处理更复杂的业务场景
         * 如下所示构造方法：表示优先执行A
         */
        CyclicBarrier cb = new CyclicBarrier(2, new A());
        new Thread(() -> {
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("thread方法");
        }).start();

        cb.await();
        System.out.println("main方法");
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println("A方法");
        }
    }
}
