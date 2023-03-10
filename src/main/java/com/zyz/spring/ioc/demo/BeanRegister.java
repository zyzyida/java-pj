package com.zyz.spring.ioc.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象注册器，这里用于单例bean的缓存，我们大幅简化，默认所有bean都是单例的。可以看到所谓单例注册，也很简单，不过是往HashMap里存对象。
 */
public class BeanRegister {
    //单例Bean缓存
    private Map<String, Object> singletonMap = new HashMap<>(32);

    /**
     * 获取单例Bean
     *
     * @param beanName bean名称
     * @return
     */
    public Object getSingletonBean(String beanName) {
        return singletonMap.get(beanName);
    }

    /**
     * 注册单例bean
     *
     * @param beanName
     * @param bean
     */
    public void registerSingletonBean(String beanName, Object bean) {
        if (singletonMap.containsKey(beanName)) {
            return;
        }
        singletonMap.put(beanName, bean);
    }
}
