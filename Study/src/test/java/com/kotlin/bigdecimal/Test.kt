package com.kotlin.bigdecimal

import org.junit.Test
import java.math.BigDecimal

class Test {

    @Test
    fun test() {
        val a = BigDecimal(1)
        val b = BigDecimal(2)
        val c = b - a
        println(c)
    }
}