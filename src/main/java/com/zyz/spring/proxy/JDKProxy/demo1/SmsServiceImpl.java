package com.zyz.spring.proxy.JDKProxy.demo1;

/**
 * 2.实现发送短信的接口
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
