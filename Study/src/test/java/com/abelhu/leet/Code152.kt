package com.abelhu.leet

import org.junit.Test
import kotlin.math.max
import kotlin.math.min


class Code152 {
    private fun maxProduct(nums: IntArray): Int {
        var max = Int.MIN_VALUE
        // 以当前节点为终结节点的最大连续子序列乘积
        var imax = 1
        // 以当前节点为终结节点的最小连续子序列乘积
        var imin = 1
        for (num in nums) {
            if (num < 0) {
                val tmp = imax
                imax = imin
                imin = tmp
            }
            imax = max(imax * num, num)
            imin = min(imin * num, num)
            max = max(max, imax)
        }
        return max
    }

    @Test
    fun test0() {
        assert(6 == maxProduct(intArrayOf(2, 3, -2, 4)))
    }

    @Test
    fun test1() {
        assert(0 == maxProduct(intArrayOf(-2, 0, -1)))
    }

    @Test
    fun test2() {
        assert(4 == maxProduct(intArrayOf(3, -1, 4)))
    }

    @Test
    fun test3() {
        assert(24 == maxProduct(intArrayOf(2, -5, -2, -4, 3)))
    }
}