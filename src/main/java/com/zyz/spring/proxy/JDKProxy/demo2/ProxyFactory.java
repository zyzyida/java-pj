package com.zyz.spring.proxy.JDKProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 3.态代理工厂:ProxyFactory，直接用反射方式生成一个目标对象的代理对象，这里用了一个匿名内部类方式重写InvocationHandler方法，实现接口重写也差不多
 */
public class ProxyFactory {
    // 维护一个目标对象
    private Object target;

    public ProxyFactory(Object target){
        this.target=target;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("请问有什么可以帮到您？");
                        // 调用目标对象方法
                        method.invoke(target, args);
                        System.out.println("问题已经解决啦！");
                        return null;
                    }
                });
    }

}
