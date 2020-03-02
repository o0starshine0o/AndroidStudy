package com.designpattern.behavior.strategy

import org.junit.Test

class Test {

    @Test
    fun test() {
        WrapStrategy(OneStrategy()).run()
        WrapStrategy(TwoStrategy()).run()
    }
}