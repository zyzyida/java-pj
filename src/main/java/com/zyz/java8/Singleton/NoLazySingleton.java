package com.zyz.java8.Singleton;

/**
 * 饿汉式单例
 */
public class NoLazySingleton {
    private NoLazySingleton(){
        System.out.println("创建NoLazySingleton实例一次");
    }

    private static NoLazySingleton noLazySingleton = new NoLazySingleton();

    public static NoLazySingleton getNoLazySingleton(){
        return noLazySingleton;
    }

    //测试
    public static void main(String[] args) {
        NoLazySingleton noLazySingleton = NoLazySingleton.getNoLazySingleton();
        NoLazySingleton noLazySingleton1 = NoLazySingleton.getNoLazySingleton();
        NoLazySingleton noLazySingleton2 = NoLazySingleton.getNoLazySingleton();
    }
}
