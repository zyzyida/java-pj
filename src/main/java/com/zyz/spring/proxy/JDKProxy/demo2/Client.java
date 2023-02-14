package com.zyz.spring.proxy.JDKProxy.demo2;

/**
 * 4.客户端：Client，生成一个代理对象实例，通过代理对象调用目标对象方法
 */
public class Client {
    public static void main(String[] args) {
        //目标对象:程序员
        ISolver developer = new Solver();
        //代理：客服小姐姐
        ISolver csProxy = (ISolver) new ProxyFactory(developer).getProxyInstance();
        //目标方法：解决问题
        csProxy.solve();
    }
}
