package com.abelhu.huawei

import org.junit.Test

class Test1 {

    fun main(n: Int, xc: Int, yc: Int, r: Int): IntArray {
        val result = MutableList(0) { 0 }
        val d1 = intArrayOf(0, 1)
        val d2 = intArrayOf(0, 1)
        val rr = r * r
        // 4个点,有点在圆内,有点在圆外
        for (j in 0 until n) for (i in 0 until n) {
            var inside = true
            var outside = true

            for (p in d1) for (q in d2) {
                val l = (xc - p - i) * (xc - p - i) + (yc - q - j) * (yc - q - j) - rr
                outside = outside and (l >= 0)
                inside = inside and (l <= 0)
            }

            // 排除掉都在圆内或者都在圆外的格子
            if (!inside && !outside) result.add(j * n + i + 1)
        }

        // 排序
        result.sort()
        return result.toIntArray()
    }

    @Test
    fun test0() {
        assert(intArrayOf(2, 3, 4, 6, 10, 14, 15, 16).contentEquals(main(4, 3, 2, 2)))
    }
}

