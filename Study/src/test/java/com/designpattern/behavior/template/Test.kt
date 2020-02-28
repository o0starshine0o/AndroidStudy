package com.designpattern.behavior.template

import org.junit.Test

class Test {

    @Test
    fun test() {
        val concrete = Concrete()
        concrete.sort()
        concrete.print()
    }
}