package com.zyz.spring.configuration;

import com.zyz.spring.ioc.DataConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean(value="config") //@Bean：加载配置类的时候，会调用@Bean注释的方法，把返回的对象存入到ioc容器中，供开发者使用
    public DataConfig dataconfig(){
        DataConfig dataConfig= new DataConfig();
        dataConfig.setDriverName("Driver");
        dataConfig.setUrl("localhost:3306/dbname");
        dataConfig.setUsername("root");
        dataConfig.setPassword("root");
        return dataConfig;
    }
}
