package com.zyz.java8.Singleton;

import java.util.HashMap;
import java.util.Map;

public class SingletonExtend {
    private static final Map<String, SingletonExtend> container = new HashMap<String, SingletonExtend>();
    private static final int MAX_NUM = 3;
    private static String CHAHE_KEY_PRE = "cache";
    private static int initNumber = 1;

    private SingletonExtend() {
        System.out.println("创建SingleExtend实例1次！");
    }

    public static SingletonExtend getInstance() {
        String key = CHAHE_KEY_PRE + initNumber;
        SingletonExtend singletonExtend = container.get(key);
        if (singletonExtend == null) {
            singletonExtend = new SingletonExtend();
            container.put(key, singletonExtend);
        }
        initNumber++;
        if (initNumber > 3) {
            initNumber = 1;
        }
        return singletonExtend;
    }

    //测试
    public static void main(String[] args) {
        SingletonExtend instance = SingletonExtend.getInstance();
        SingletonExtend instance1 = SingletonExtend.getInstance();
        SingletonExtend instance2 = SingletonExtend.getInstance();
        SingletonExtend instance3 = SingletonExtend.getInstance();
        SingletonExtend instance4 = SingletonExtend.getInstance();
        SingletonExtend instance5 = SingletonExtend.getInstance();
        SingletonExtend instance6 = SingletonExtend.getInstance();
        SingletonExtend instance7 = SingletonExtend.getInstance();
        SingletonExtend instance8 = SingletonExtend.getInstance();
        SingletonExtend instance9 = SingletonExtend.getInstance();
        System.out.println(instance);
        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance3);
        System.out.println(instance4);
        System.out.println(instance5);
        System.out.println(instance6);
        System.out.println(instance7);
        System.out.println(instance8);
        System.out.println(instance9);
    }

}
