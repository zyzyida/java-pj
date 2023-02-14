package com.zyz.java8;

import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class testJava8 {

    public static void main(String[] args) {
        // 反射
        extracted();

        extracted1();

        extracted2();

        extracted8();

        testJava8 testJava8 = new testJava8();
        testJava8.testRunnable();
        testJava8.testComparator();
        testJava8.lambdaFor();

        LambdaClass.forEg();
    }

    public void testRunnable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("The runnable now is using!");
            }
        }).start();

        //用lambda表达式
        new Thread(() -> System.out.println("It's a lambda function!")).start();
    }

    public void testComparator() {
        List<Integer> strings = Arrays.asList(1, 2, 3);
        Collections.sort(strings, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        //用lambda表达式
        Collections.sort(strings, (Integer o1, Integer o2) -> o2 - o1);

        System.out.println(strings);
    }

    public void testListener() {
        JButton jButton = new JButton();
        jButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                e.getItem();
            }
        });

        //用lambda表达式
        jButton.addItemListener(e -> e.getItem());
    }

    public void lambdaFor() {
        List<String> strings = Arrays.asList("1", "2", "3");
        //传统foreach
        for (String s : strings) {
            System.out.println(s);
        }
        //lambda foreach
        strings.forEach((s) -> System.out.println(s));
        //or
        strings.forEach(System.out::println);
        //map
        Map<Integer, String> map = new HashMap<>();
        map.forEach((k,v)-> System.out.println(v));
    }

    private static void extracted2() {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        Class<? extends List> clazz = list.getClass();
        Method add = null;
        try {
            add = clazz.getDeclaredMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //但是通过反射添加是可以的
        //这就说明在运行期间所有的泛型信息都会被擦掉
        try {
            add.invoke(list, "kl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);
    }

    private static void extracted1() {
        Person person1 = new Person(new Address("武汉"));
        System.out.println("###########浅拷贝#############");
        Person person1Copy = person1.clone();
        System.out.println(person1.getAddress() == person1Copy.getAddress());

        System.out.println("###########深拷贝#############");
        Person person2Copy = person1.clone1();
        System.out.println(person1.getAddress() == person2Copy.getAddress());
    }

    private static void extracted() {
        try {
            /**
             * 获取 TargetObject 类的 Class 对象并且创建 TargetObject 类实例
             */
            Class<?> targetClass = Class.forName("com.zyz.java8.TargetObject");
            TargetObject targetObject = (TargetObject) targetClass.newInstance();

            /**
             * 获取 TargetObject 类中定义的所有方法
             */
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
            }

            /**
             * 获取指定方法并调用
             */
            Method publicMethod = targetClass.getDeclaredMethod("publicMethod",
                    String.class);

            publicMethod.invoke(targetObject, "JavaGuide");

            /**
             * 获取指定参数并对参数进行修改
             */
            Field field = targetClass.getDeclaredField("value");
            //为了对类中的参数进行修改我们取消安全检查
            field.setAccessible(true);
            field.set(targetObject, "JavaGuide");

            /**
             * 调用 private 方法
             */
            Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
            //为了调用private方法我们取消安全检查
            privateMethod.setAccessible(true);
            privateMethod.invoke(targetObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //浅拷贝与深拷贝
    public static class Address implements Cloneable {
        private String name;

        public Address(String name) {
            this.name = name;
        }

        @Override
        protected Address clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    public static class Person implements Cloneable {
        private Address address;

        public Person(Address address) {
            this.address = address;
        }

        @Override
        public Person clone() {
            try {
                Person person = (Person) super.clone(); //浅拷贝
                return person;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        public Person clone1() {
            try {
                Person person = (Person) super.clone();
                person.setAddress(person.getAddress().clone()); //深拷贝
                return person;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        private void setAddress(Address address1) {
            this.address = address1;
        }

        public Address getAddress() {
            return this.address;
        }
    }

    //3.泛型方法
    public static <E> void printArray(E[] inputArray) {
        for (E element : inputArray) {
            System.out.printf("%s ", element);
        }
        System.out.println();
    }

    private static void extracted8() {
        System.out.println("########################");
        List<String> list = Lists.newArrayList("1", "2", "3", "1");
        Map<String, List<String>> map = list.stream().collect(Collectors.toMap(key -> key,
                value -> Lists.newArrayList(value),
                (List<String> newValueList, List<String> oldValueList) -> {
                    oldValueList.addAll(newValueList);
                    return oldValueList;
                }));
        System.out.println("¥¥¥¥¥¥¥¥");

        Map<String, Integer> map22 = new HashMap<>(3);
        map22.put("ccc", 1);
        map22.put("aaa", 3);
        map22.put("bbb", 2);

        // 注意：这里不能用HashMap存，HashMap的遍历顺序是随机的
        // Collectors.toMap()默认是HashMap
        TreeMap<String, Integer> result = map22.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, TreeMap::new));
        System.out.println("result: " + result);

        int[][] ArrayA = {{1, 7}, {21, 6}, {4, 12}};//三行四列
        System.out.println(ArrayA[0].length);
        int[][] ArrayB = new int[ArrayA[0].length][ArrayA.length];//四行三列

        System.out.println("原数组：");
        PrintArray(ArrayA);

        System.out.println("==================================");
        //转化
        for (int i = 0; i < ArrayA.length; i++) {
            for (int j = 0; j < ArrayA[i].length; j++) {
                ArrayB[j][i] = ArrayA[i][j];
            }

        }
        System.out.println("转化后:");
        PrintArray(ArrayB);
        System.out.println();
    }

    public static void PrintArray(int[][] Array) {
        for (int i = 0; i < Array.length; i++) {
            for (int j = 0; j < Array[i].length; j++) {
                System.out.print(Array[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
