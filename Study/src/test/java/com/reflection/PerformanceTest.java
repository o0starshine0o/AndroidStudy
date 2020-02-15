package com.reflection;

import java.lang.reflect.Method;

public class PerformanceTest {

    public static void main(String[] args) throws Exception {
        int testTime = 40000000;
        PerformanceTest test = new PerformanceTest();
        String msg = "this is test message";
        long bTime = System.currentTimeMillis();
        for (int i = 0; i < testTime; i++) {
            test.takeAction(msg);
        }
        long eTime = System.currentTimeMillis();
        System.out.println("origin function:" + (eTime - bTime));

        Method method = test.getClass().getMethod("takeAction", String.class);

        bTime = System.currentTimeMillis();
        for (int i = 0; i < testTime; i++) {
            method.invoke(test, msg);
        }
        eTime = System.currentTimeMillis();
        System.out.println("reflection function:" + (eTime - bTime));

        bTime = System.currentTimeMillis();
        method.setAccessible(true);
        for (int i = 0; i < testTime; i++) {
            method.invoke(test, msg);
        }
        eTime = System.currentTimeMillis();
        System.out.println("reflection function with setAccessible:" + (eTime - bTime));


    }

    public int takeAction(String msg) {
        return (msg.length() * (int) (System.currentTimeMillis() % 100000));
    }

}
