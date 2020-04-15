package com.designpattern.structure.bridge

import org.junit.Test

class Test {

    @Test
    fun test() {
        // real 和 bridge都可以独立变化，不影响
        Real(MyBridge2()).draw()
        Real2(MyBridge()).draw()
    }
}