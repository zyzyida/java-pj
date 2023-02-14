package com.zyz.spring.ioc.demo;

import org.junit.Test;

/**
 * Spring的IOC容器工作的过程，其实可以划分为两个阶段：容器启动阶段和Bean实例化阶段。
 *
 * 其中容器启动阶段主要做的工作是加载和解析配置文件，保存到对应的Bean定义中。
 * 容器启动阶段：
 * 1.加载配置
 * 2.分析配置信息
 * 3.装配到BeanDefinition
 * 4.其他后处理
 *
 * Bean实例化阶段：
 * 1.实例化对象
 * 2.装配依赖
 * 3.生命周期回调
 * 4.对象其他处理
 * 5.注册回调接口
 */
public class ApiTest {
    @Test
    public void test_BeanFactory() {
        System.out.println("******");
        //1.创建bean工厂(同时完成了加载资源、创建注册单例bean注册器的操作)
        BeanFactory beanFactory = new BeanFactory();

        //2.第一次获取bean（通过反射创建bean，缓存bean）
        UserDao userDao1 = (UserDao) beanFactory.getBean("userDao");
        userDao1.queryUserInfo();

        //3.第二次获取bean（从缓存中获取bean）
        UserDao userDao2 = (UserDao) beanFactory.getBean("userDao");
        userDao2.queryUserInfo();
    }
}
