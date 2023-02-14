package com.zyz.java8.Singleton;

/**
 * 线程安全懒汉式单例 实例：
 * 如何让 懒汉式单例在多线程下发挥正确的作用呢？那就是在访问单例实例的方法处进行同步
 */
public class SafeLazySingleton {
    private SafeLazySingleton() {
        System.out.println("生成SaveLazySingleton实例一次");
    }

    private static SafeLazySingleton safeLazySingleton = null;

    //对整个访问实例的方法进行同步
    public synchronized static SafeLazySingleton getSafeLazySingleton() {
        if (safeLazySingleton == null) {
            safeLazySingleton = new SafeLazySingleton();
        }
        return safeLazySingleton;
    }

    //对必要的代码块进行同步
    public static SafeLazySingleton getInstance() {
        if (safeLazySingleton == null) {
            synchronized (SafeLazySingleton.class) {
                if (safeLazySingleton == null) {
                    safeLazySingleton = new SafeLazySingleton();
                }
            }
        }
        return safeLazySingleton;
    }

    //测试
    public static void main(String[] args) {
        SafeLazySingleton safeLazySingleton = SafeLazySingleton.getSafeLazySingleton();
        SafeLazySingleton safeLazySingleton1 = SafeLazySingleton.getSafeLazySingleton();
        SafeLazySingleton safeLazySingleton2 = SafeLazySingleton.getSafeLazySingleton();

        SafeLazySingleton instance = SafeLazySingleton.getInstance();
        SafeLazySingleton instance1 = SafeLazySingleton.getInstance();
        SafeLazySingleton instance2 = SafeLazySingleton.getInstance();
    }

}
