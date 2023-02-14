package com.zyz.spring.proxy.JDKProxy.demo2;

/**
 * 2.目标类:需要实现对应接口
 */
public class Solver implements ISolver {
    @Override
    public void solve() {
        System.out.println("疯狂掉头发解决问题……");
    }
}
