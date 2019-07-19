package com.bjs.part04.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description
 * @Author BianJiashuai
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    // 线程池最大数量
    public static final int MAX_WORKER_NUMBER = 10;
    // 线程池默认数量
    public static final int DEFAULT_WORKER_NUMBER = 5;
    // 线程池最小数量
    public static final int MIN_WORKER_NUMBER = 1;

    // 工作列表
    private final LinkedList<Job> jobs = new LinkedList<>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    // 工作者线程数量
    private int workerNum = DEFAULT_WORKER_NUMBER;

    // 线程编号生成
    AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorker(DEFAULT_WORKER_NUMBER);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBER ? MAX_WORKER_NUMBER : num < MIN_WORKER_NUMBER ? MIN_WORKER_NUMBER : num;
        initializeWorker(workerNum);
    }

    // 初始化线程工作者
    private void initializeWorker(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            // 添加一个工作，然后进行通知
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorker(int num) {
        synchronized (jobs) {
            // 限制新增的worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBER) {
                num = MAX_WORKER_NUMBER - this.workerNum;
            }
            initializeWorker(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("超出工作线程数量");
            }

            // 按照给定的的数量停止worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    // 工作者，负责消费任务
    class Worker implements Runnable {
        // 是否工作
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job;
                synchronized (jobs) {
                    // 如果工作列表是空, 那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部中断，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // 取出一个job
                    job = jobs.removeFirst();
                }

                if (job != null) {
                    job.run();
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
