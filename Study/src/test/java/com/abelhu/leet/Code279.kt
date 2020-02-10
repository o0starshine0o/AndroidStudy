package com.abelhu.leet

import org.junit.Test

class Code279 {
    fun numSquares(n: Int): Int {
        val dp = IntArray(n + 1) { i -> i }
        for (i in 1..n) {
            var j = 1
            while (i >= j * j) {
                dp[i] = kotlin.math.min(dp[i], dp[i - j * j] + 1)
                j++
            }
        }
        return dp.last()
    }

    @Test
    fun test0() = assert(3 == numSquares(12))

    @Test
    fun test1() = assert(2 == numSquares(13))
}