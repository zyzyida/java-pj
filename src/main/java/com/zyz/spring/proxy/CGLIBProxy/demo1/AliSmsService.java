/**
 * JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。
 *
 * 为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。
 */
package com.zyz.spring.proxy.CGLIBProxy.demo1;

import com.zyz.spring.proxy.JDKProxy.demo1.JdkProxyFactory;
import com.zyz.spring.proxy.JDKProxy.demo1.SmsService;
import com.zyz.spring.proxy.JDKProxy.demo1.SmsServiceImpl;

/**
 * 1.实现一个使用阿里云发送短信的类
 */
public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }

    public static void main(String[] args) {
        SmsService proxy = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        proxy.send("java");
    }
}
