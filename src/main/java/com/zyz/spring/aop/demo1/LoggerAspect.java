package com.zyz.spring.aop.demo1;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerAspect {

    @Before("execution(public int com.zyz.spring.aop.demo1.CalImpl.*(..))")
    public void before(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法的参数是：" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(public int com.zyz.spring.aop.demo1.CalImpl.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法的结果是：" + result);
    }
}
