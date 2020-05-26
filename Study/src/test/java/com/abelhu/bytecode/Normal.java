package com.abelhu.bytecode;

class Normal {
    void test() throws InterruptedException {
        Thread.sleep(1000);
    }

    // 用于测定test方法的执行时间，由javassist创建
//    void test$agent() throws InterruptedException {
//        long start = System.currentTimeMillis();
//        test();
//        System.out.println("function test() take: " + (System.currentTimeMillis() - start) + "ms");
//    }

    static void testStatic() {
    }
}