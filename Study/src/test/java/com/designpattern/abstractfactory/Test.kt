package com.designpattern.abstractfactory

import org.junit.Test

class Test {
    @Test
    fun test0() = assert(Factory().create0().show())

    @Test
    fun test1() = assert(!Factory().create1().show())
}