package com.abelhu.bytecode;

public class StaticJava {
    static void testStatic(Normal normal) throws InterruptedException {
        normal.test();
        System.out.println("fun test changed to java code with static use");
    }
}
