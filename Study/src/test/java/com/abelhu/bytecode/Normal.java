package com.abelhu.bytecode;

public class Normal {
    void test() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Normal class test() sleep 1000ms");
    }

    void testRedirect() {
        System.out.println("Normal class testRedirect()");
    }

    // 用于测定test方法的执行时间，由javassist创建
//    void test$agent() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        test();
//        System.out.println("function test() take: " + (System.currentTimeMillis() - start) + "ms");
//    }
}