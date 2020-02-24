package com.designpattern.factory

import org.junit.Test

class Test {
    @Test
    fun test0() = assert(Factory().createProduct().productFun())

}