package com.bjs.part06;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Description 使用Fork/Join框架计算两个值范围内的自然数之和
 * @Author BianJiashuai
 */
public class CountTask extends RecursiveTask<Integer> {

    public static final int THRESHOLD = 2; // 任务阈值
    private int start;
    private int end;
    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName());
        int sum = 0;
        // 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            // 计算任务结果
            for (int i = start; i<= end; i++) {
                sum += i;
            }
        } else {
            // 分割成两个子任务
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行完成，并得到结果
            int leftRes = leftTask.join();
            int rightRes = rightTask.join();

            // 合并子任务
            sum = leftRes + rightRes;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("请输入两个自然数，中间用空格分开，第二个要大于第一个数:");
        Scanner sc = new Scanner(System.in);
        String nextLine = sc.nextLine();
        String[] s = nextLine.split(" ");
        int start = Integer.parseInt(s[0]);
        int end = Integer.parseInt(s[1]);
        CountTask task = new CountTask(start, end);
        ForkJoinTask<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
