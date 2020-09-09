package com.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

class TransferCodeJava {
    class Apple {
        int color = 1;
        int weight = 1;

        Apple(int color, int weight) {
            this.color = color;
            this.weight = weight;
        }
    }

    interface Predicate<T> {
        boolean test(T t);
    }

    static boolean isGreen(Apple apple) {
        return apple.color == 1;
    }

    static boolean isHeavy(Apple apple) {
        return apple.weight > 5;
    }

    static List<Apple> filter(List<Apple> inventory, Predicate<Apple> predicate) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : inventory) {
            if (predicate.test(apple)) {
                list.add(apple);
            }
        }
        return list;
    }

    @Test
    void appleTest() {
        List<Apple> list = new ArrayList<>();
        list.add(new Apple(1, 1));
        list.add(new Apple(1, 10));
        // !!!注意:isGreen只是一个方法,并不是实现了Predicate的类
        List<Apple> result = filter(list, TransferCodeJava::isGreen);
        for (Apple apple : result) {
            System.out.println(apple);
        }
        // !!!注意:isHeavy只是一个方法,并不是实现了Predicate的类
        List<Apple> result2 = filter(list, TransferCodeJava::isHeavy);
        for (Apple apple : result2) {
            System.out.println(apple);
        }
    }
}
