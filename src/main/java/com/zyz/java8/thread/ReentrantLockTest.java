package com.zyz.java8.thread;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final int BUFFERSIZE = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(BUFFERSIZE);

    public void put() {
        Random random = new Random();
        lock.lock();
        try {
            //对于生产者来说，如果队列满了，则需要被阻塞
            while (queue.size() == BUFFERSIZE) {
                System.out.println(Thread.currentThread().getName() + "队列已满，生产者被阻塞");
                notFull.await();
            }
            queue.add(random.nextInt());//继续生产
            Thread.sleep(1000);
            notEmpty.signal();//唤醒消费线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                System.out.println(Thread.currentThread().getName() + "队列为空，消费者被阻塞");
                notEmpty.await();
            }
            int d = queue.poll();
            System.out.println(Thread.currentThread().getName() + "消费的数字为：" + d);
            Thread.sleep(1000);
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

class Producer implements Runnable {

    public Buffer buffer;

    public Producer(Buffer b) {
        buffer = b;
    }

    @Override
    public void run() {
        while (true) {
            buffer.put();
        }
    }
}

class Consumer implements Runnable {

    public Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    @Override
    public void run() {
        buffer.take();
    }
}

public class ReentrantLockTest {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        //创建两个生产者
        for (int i = 0; i < 2; i++) {
            new Thread(producer, "生产者-" + i).start();
        }
        //创建三个消费者
        for (int i = 0; i < 3; i++) {
            new Thread(consumer, "消费者-" + i).start();
        }

        //ReentrantReadWriteLock
        Demo demo = new Demo();
        DemoThread t1 = new DemoThread(demo);
        DemoThread t2 = new DemoThread(demo);
        t1.start();
        t2.start();
    }
}

class Demo {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void read() {
        rwl.readLock().lock();
        int i = 0;
        while (i++ < 100) {
            System.out.println(Thread.currentThread().getName() + "正在进行读操作");
        }
        System.out.println(Thread.currentThread().getName() + "读操作完毕");
        rwl.readLock().unlock();
    }
}

class DemoThread extends Thread {
    private Demo demo;

    public DemoThread(Demo demo) {
        this.demo = demo;
    }

    public void run() {
        demo.read();
    }
}
