package com.zyz.spring.aop.JDKProxy.demo1;

/**
 * 1.定义发送短信的接口
 */
public interface SmsService {
    String send(String message);
}
