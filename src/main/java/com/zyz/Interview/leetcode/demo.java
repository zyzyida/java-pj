package com.zyz.Interview.leetcode;

public class demo {
    public static void main(String[] args) {
//        test(8);
        outClass outClass = new outClass();
        outClass.outPrint(3);
    }

    public static void test(final int b){
        final int a=10;
        new Thread(){
            @Override
            public void run() {
                System.out.println(a);
                System.out.println(b);
            }
        }.start();
    }
}

class outClass{
    private int age=12;
    public void outPrint(final int x){
        class inClass{
            public void inPrint(){
                System.out.println(x);
                System.out.println(age);
            }
        }
        new inClass().inPrint();
    }
}
