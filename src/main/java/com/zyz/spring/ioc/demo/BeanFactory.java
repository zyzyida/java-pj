package com.zyz.spring.ioc.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象工厂，我们最核心的一个类，在它初始化的时候，创建了bean注册器，完成了资源的加载。
 * <p>
 * 获取bean的时候，先从单例缓存中取，如果没有取到，就创建并注册一个bean
 */
public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private BeanRegister beanRegister;

    public BeanFactory() {
        //创建bean注册器
        beanRegister = new BeanRegister();
        //加载资源
        this.beanDefinitionMap = new ResourceLoader().getResource();
    }

    /**
     * 获取bean
     *
     * @param beanName bean名称
     * @return
     */
    public Object getBean(String beanName) {
        //从bean缓存中取
        Object singletonBean = beanRegister.getSingletonBean(beanName);
        if (singletonBean != null) {
            return singletonBean;
        }
        return createBean(beanDefinitionMap.get(beanName));
    }

    /**
     * 创建Bean
     *
     * @param beanDefinition bean定义
     * @return
     */
    public Object createBean(BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            //缓存bean
            beanRegister.registerSingletonBean(beanDefinition.getBeanName(), bean);
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
