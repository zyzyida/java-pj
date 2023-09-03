package com.zyz.java8.thread;

public class Test {
    private Object lock = new Object();
    private boolean envReady = false;

    private class WorkerThread extends Thread {
        public void run() {
            System.out.println("线程 WorkerThread 等待拿锁");
            synchronized (lock) {
                try {
                    //执行一些费时的操作
                    System.out.println("线程 WorkerThread 拿到锁");
                    if (!envReady) {
                        System.out.println("线程 WorkerThread 放弃锁");
                        lock.wait();
                    }
                    //需要使用准备好的环境
                    System.out.println("线程 WorkerThread 收到通知后继续执行");
                } catch (InterruptedException e) {

                }
            }
        }
    }

    private class PrepareEnvThread extends Thread {
        public void run() {
            System.out.println("线程 PrepareEnvThread 等待拿锁");
            synchronized (lock) {
                System.out.println("线程 PrepareEnvThread 拿到锁");
                //这个线程做一些初始化环境的工作后通知 WorkerThread
                envReady = true;
                lock.notify();
                System.out.println("通知 WorkerThread");
            }
        }
    }

    public void prepareEnv() {
        new PrepareEnvThread().start();
    }

    public void work() {
        new WorkerThread().start();
    }

    public static void main(String[] args) {
        Test t = new Test();
        //模拟工作线程先开始执行的情形
        t.work();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.prepareEnv();
    }

}
