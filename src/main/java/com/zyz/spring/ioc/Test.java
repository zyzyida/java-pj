package com.zyz.spring.ioc;

import com.zyz.spring.configuration.BeanConfiguration;
import com.zyz.spring.ioc.DataConfig;
import com.zyz.spring.ioc.GlobalConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * IOC:控制反转
 * 不用IOC：所有对象开发者自己创建
 * 使用IOC：对象不用开发者创建，而是交给Spring框架来完成
 *
 * 使用方式：基于XML
 *         基于注解(1.配置类 2.扫包+注解)
 *
 * ioc自动创建对象，完成依赖注入
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
