package com.zyz.java8.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

    private static final int CORE_POOL_SIZE = 5;//线程池的核心线程数量
    private static final int MAX_POOL_SIZE = 10;//线程池的最大线程数
    private static final int QUEUE_CAPACITY = 100;//任务队列为 ArrayBlockingQueue，并且容量为 100
    private static final long KEEP_ALIVE_TIME = 1L;//当线程数大于核心线程数时，多余的空闲线程存活的最长时间

    public static void run() {
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,//时间单位
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),//任务队列，用来储存等待执行任务的队列
                new ThreadPoolExecutor.CallerRunsPolicy()//饱和策略
        );

        for (int i = 0; i < 10; i++) {
            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
            Runnable worker = new MyRunnable("" + i);
            //执行Runnable
            threadPoolExecutor.execute(worker);
        }

        //终止线程池
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
        }
        System.out.println("Finished all threads");

    }

    public static void main(String[] args) {
        ThreadPoolExecutorDemo.run();
    }

}
