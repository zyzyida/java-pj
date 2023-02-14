package com.zyz.spring.aop.demo2.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * <p>
 * SpringBoot启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-28 14:49
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class HelloWorldDemoApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(HelloWorldDemoApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
