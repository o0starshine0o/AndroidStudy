package com.designpattern.behavior.responsibility

import org.junit.Test

class Test {
    @Test
    fun test() {
        ConcreteHandler().apply { handler = ConcreteHandler2() }.handlerRequest()
    }
}