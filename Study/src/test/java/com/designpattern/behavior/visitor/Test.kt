package com.designpattern.behavior.visitor

import org.junit.Test

class Test {
    @Test
    fun test() {
        val data = Struct.getData()
        data.forEach { element -> element?.accept(Visitor()) }
    }
}