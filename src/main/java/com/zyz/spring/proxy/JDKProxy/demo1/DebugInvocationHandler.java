package com.zyz.spring.proxy.JDKProxy.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 3.定义一个 JDK 动态代理类
 */
public class DebugInvocationHandler implements InvocationHandler {

    //代理类中的真实对象
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());

        //调用原生方法（被代理类的方法）
        Object result = method.invoke(target, args);

        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());

        return result;
    }
}
