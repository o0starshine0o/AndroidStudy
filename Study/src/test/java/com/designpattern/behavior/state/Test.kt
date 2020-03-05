package com.designpattern.behavior.state

import org.junit.Test

class Test {

    @Test
    fun test() {
        val context = Context()
        context.state = ConcreteStateA()
        context.handle()
        context.state = ConcreteStateB()
        context.handle()
    }
}