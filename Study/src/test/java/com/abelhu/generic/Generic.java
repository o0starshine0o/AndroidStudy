package com.abelhu.generic;

import org.junit.Test;

public class Generic {

    /**
     * 带`extends`使得类型变成了`协变(covariant)`
     * 上界为`Integer`
     * 只能是`Integer`或者是`Integer`的子类
     */
    class A<T extends Integer> {
        T value;

        A(T a) {
            value = a;
        }
    }

    interface Source<T> {
        T next();
    }

    /**
     * 必须声明`o`的类型为`Source<? extends Object>`，这样才能接收`Source<String>`
     */
    void test(Source<String> s) {
        Source<? extends Object> o = s;
        // 这是不允许的，因为`Source<String>`并不是`Source<Object>`的子类
        // Source<Object> o = s;
    }

    @Test
    public void test0() {
        assert (8 == new A<>(8).value);
    }

    @Test
    public void test1() {

    }
}
