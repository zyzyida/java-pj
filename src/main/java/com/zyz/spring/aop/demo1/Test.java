package com.zyz.spring.aop.demo1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * aop：Aspect-oriented Programming
 * 面向切面编程:把重复的代码抽离出来，抽象成一个对象，统一处理
 *
 * 1.创建切面类
 * 2.实现类添加@Component注解
 * 3.配置自动扫包，开启自动生成代理对象
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext context3= new AnnotationConfigApplicationContext("com.zyz.spring.aop");
        System.out.println(context3.getBean(Cal.class));
        CalImpl bean = context3.getBean(CalImpl.class);
        System.out.println(bean.add(9, 8));
    }
}
