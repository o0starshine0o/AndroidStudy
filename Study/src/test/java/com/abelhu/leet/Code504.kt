package com.abelhu.leet

import org.junit.Test

class Code504 {
    private fun convertToBase7(num: Int): String {
        val negative = num < 0
        // 商
        var quotient = if (negative) -num else num
        // 余数
        var remainder: Int
        // 结果
        var result = if (quotient > 0) "" else "0"
        // 连除，取余数的逆序
        while (quotient > 0) {
            remainder = quotient % 7
            result += remainder
            quotient /= 7
        }
        if (negative) result += "-"
        return result.reversed()
    }

    @Test
    fun test0() {
        assert("202" == convertToBase7(100))
    }

    @Test
    fun test1() {
        assert("-10" == convertToBase7(-7))
    }

    @Test
    fun test2() {
        assert("0" == convertToBase7(0))
    }
}