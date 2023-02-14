package com.zyz.java8;

/**
 * 使用函数式接口
 */
public class LambdaClass extends LambdaClassSuper{
    public static void forEg() {
        lambdaInterfaceDemo(() -> System.out.println("自定义函数式接口"));
    }

    //函数式接口参数
    private static void lambdaInterfaceDemo(LambdaInterface i) {
        System.out.println(i);
    }

    public static LambdaInterface staticF(){
        return null;
    }

    public LambdaInterface f(){
        return null;
    }

    public void show(){
        //1.调用静态函数，返回类型必须是functional-interface
        LambdaInterface t = LambdaClass::staticF;

        //2.实例方法调用
        LambdaClass lambdaClass = new LambdaClass();
        LambdaInterface lambdaInterface = lambdaClass::f;

        //3.超类上的方法调用
        LambdaInterface superf = super::sf;

        //4. 构造方法调用
        LambdaInterface tt = LambdaClassSuper::new;
    }

}
