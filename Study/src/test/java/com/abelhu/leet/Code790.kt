package com.abelhu.leet

import org.junit.Test

class Code790 {


    fun numTilings(n: Int): Int {
        val mod = 1000000007
        return when (n) {
            0 -> 0
            1 -> 1
            2 -> 2
            else -> {
                val dp = IntArray(n + 1)
                dp[0] = 1
                dp[1] = 1
                dp[2] = 2
                for (i in 3..n) dp[i] = (2 * dp[i - 1] % mod + dp[i - 3] % mod) % mod
                return dp.last()
            }
        }
    }

    @Test
    fun test3() = assert(5 == numTilings(3))

    @Test
    fun test4() = assert(11 == numTilings(4))

    @Test
    fun test5() = assert(24 == numTilings(5))

    @Test
    fun test28() = assert(914332884 == numTilings(28))

    @Test
    fun test29() = assert(222194076 == numTilings(29))

    @Test
    fun test30() = assert(312342182 == numTilings(30))

}