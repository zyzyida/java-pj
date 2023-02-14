package com.zyz.java8;

//1.泛型类
//此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
//在实例化泛型类时，必须指定T的具体类型
class Generic<T> {
    private T key;
    public Generic(T key) {
        this.key = key;
    }
    public T getKey() {
        return key;
    }
}

//2.泛型接口
public interface Generator<T> {
    public T method();
}

class GeneratorImpl<T> implements Generator<T>{
    @Override
    public T method() {
        return null;
    }
}

class GeneratorImpl1 implements Generator<String>{
    @Override
    public String method() {
        return "hello";
    }
}

//3.泛型方法

