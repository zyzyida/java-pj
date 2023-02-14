package com.zyz.java8.Singleton;

/**
 * 懒汉式单例
 * <p>
 * 懒汉式在多线程环境下面是有问题的，下面演示这个多线程环境下很有可能出现的问题：
 */
public class LazySingleton {
    private LazySingleton() {
        System.out.println("生成一个LazySingleton实例一次");
    }

    private static LazySingleton lazySingleton = null;

    public static LazySingleton getLazySingleton() {
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

    //测试
    public static void main(String[] args) {
//        LazySingleton lazySingleton = LazySingleton.getLazySingleton();
//        LazySingleton lazySingleton1 = LazySingleton.getLazySingleton();
//        LazySingleton lazySingleton2 = LazySingleton.getLazySingleton();

        // 我们新建10个线程，让这10个线程同时调用LazySingleton.getLazyInstance()方法
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run(){
                    LazySingleton.getLazySingleton();
                }
            }.start();
        }
    }
}
