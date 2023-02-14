package com.zyz.spring.ioc.demo;

import lombok.Data;

/**
 * bean定义类，配置文件中bean定义对应的实体
 */
@Data
public class BeanDefinition {
    private String beanName;

    private Class beanClass;
}
