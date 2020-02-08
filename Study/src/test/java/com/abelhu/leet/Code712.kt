package com.abelhu.leet

import org.junit.Test

class Code712 {
    fun minimumDeleteSum(s1: String, s2: String): Int {
        // dp[i,j] = minimumDeleteSum(s1[0:i], s2[0:j])
        val dp = Array(s1.length + 1) { Array(s2.length + 1) { 0 } }
        // dp[i,j] = min(dp[i-1,j] + s1[i], dp[i, j-1] + s2[j])
        for (i in 0..s1.length) {
            for (j in 0..s2.length) {
                if (i == 0 && j == 0) dp[i][j] = 0
                else if (i == 0) dp[i][j] = dp[i][j - 1] + s2[j - 1].toInt()
                else if (j == 0) dp[i][j] = dp[i - 1][j] + s1[i - 1].toInt()
                else if (s1[i - 1] == s2[j - 1]) dp[i][j] = dp[i - 1][j - 1]
                else dp[i][j] = kotlin.math.min(dp[i][j - 1] + s2[j - 1].toInt(), dp[i - 1][j] + s1[i - 1].toInt())
            }
        }
        return dp.last().last()
    }

    @Test
    fun test0() = assert(231 == minimumDeleteSum("sea", "eat"))

    @Test
    fun test1() = assert(403 == minimumDeleteSum("delete", "leet"))
}