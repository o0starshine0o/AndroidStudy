package com.kotlin.interfacee

import org.junit.Test

class InterfaceTest {
    @Test
    fun test() {

        JavaView().test {

        }

        // 看来看仔细官方文档很重要，就比如说这里，说是Java函数式接口就一定不能用kotlin写“函数式接口”，否则就是你的错
        // https://blog.csdn.net/a907691592/article/details/79296008
//        KotlinView().test {
//
//        }
    }
}