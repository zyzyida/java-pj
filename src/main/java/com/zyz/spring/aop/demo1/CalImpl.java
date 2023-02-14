package com.zyz.spring.aop.demo1;

import com.zyz.spring.aop.demo1.Cal;
import org.springframework.stereotype.Component;

/**
 * 计算器方法中，日志和业务混合在一起，AOP要做的就是将日志代码全部抽象出去统一进行处理，计算器方法中只保留核心的业务代码。
 * 底层使用动态代理机制来实现的。
 * 做到核心业务和非业务代码的解耦合。
 *
 */
@Component
public class CalImpl implements Cal {
    @Override
    public int add(int num1, int num2) {
        int result = num1 + num2;
        return result;
    }

    @Override
    public int sub(int num1, int num2) {
        int result = num1 - num2;
        return result;
    }

    @Override
    public int mul(int num1, int num2) {
        int result = num1 * num2;
        return result;
    }

    @Override
    public int div(int num1, int num2) {
        int result = num1 / num2;
        return result;
    }
}
