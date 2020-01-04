package com.abelhu.leet

import org.junit.Test

class Code446 {
    private fun numberOfArithmeticSlices(A: IntArray): Int {
        // 保存临时变量
        val mapArray = Array<HashMap<Int, Int>>(A.size) { i -> HashMap(i) }
        var result = 0
        for ((i, a) in A.withIndex()) {
            for (j in 0 until i) {
                val d = a.toLong() - A[j].toLong()
                if (d > Int.MAX_VALUE || d < Int.MIN_VALUE) continue
                val sum = mapArray[j].getOrDefault(d.toInt(), 0)
                val origin = mapArray[i].getOrDefault(d.toInt(), 0)
                mapArray[i][d.toInt()] = origin + sum + 1
                result += sum
            }
        }
        return result
    }

    @Test
    fun test() {
//        assert(7 == numberOfArithmeticSlices(intArrayOf(2, 4, 6, 8, 10)))
        assert(2 == numberOfArithmeticSlices(intArrayOf(2, 2, 3, 4)))
    }
}