package com.zyz.spring.ioc;

import com.zyz.spring.configuration.BeanConfiguration;
import com.zyz.spring.ioc.DataConfig;
import com.zyz.spring.ioc.GlobalConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * IOC:控制反转，Inversion of Control
 * 不用IOC：所有对象开发者自己创建
 * 使用IOC：对象不用开发者创建，而是交给Spring Ioc Container来完成
 *
 * 使用方式：基于XML
 *         基于注解(1.配置类 2.扫包+注解)
 *
 * ioc自动创建对象，完成依赖注入。
 * 容器负责创建、配置和管理Bean，也就是它管理者bean的生命，控制着bean的依赖注入。
 * Bean：其实就是包装了的Object
 *
 * 依赖注入：dependency injection
 * 依赖：程序运行需要依赖外部的资源，一共程序内对象所需要的数据、资源。
 * 注入：配置文件把资源从外部注入到内部，容器加载了外部的文件、对象、数据，然后把这个资源注入给程序内的对象，维护了程序内外对象之间的依赖关系。
 *
 * 所以说，控制反转是通过依赖注入实现的。从而实现了对象之间的 解藕。
 */

public class Test {

    public static void main(String[] args) {

        //1.基于XML
//        ApplicationContext context1 = new ClassPathXmlApplicationContext("springIoc.xml");
//        System.out.println(context1.getBean("config"));

        //2.基于注解(1.配置类)
        //启动Spring容器
        ApplicationContext context2= new AnnotationConfigApplicationContext(BeanConfiguration.class);
        System.out.println(context2.getBean(DataConfig.class));
        System.out.println(context2.getBean("config"));

        //3.基于注解(2.扫包+注解)
        ApplicationContext context3= new AnnotationConfigApplicationContext("com.zyz.spring.ioc");
        System.out.println(context3.getBean(DataConfig.class));
        System.out.println(context3.getBean(GlobalConfig.class));

    }
}
