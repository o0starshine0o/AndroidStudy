package com.designpattern.create.builder

import org.junit.Test

class Test {
    @Test
    fun test0() = assert("Type0:Name0" == Director().create0().show())

    @Test
    fun test1() = assert("Type1:Name1" == Director().create1().show())
}