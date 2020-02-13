package com.abelhu.leet

import org.junit.Test

class Code1310 {
    fun xorQueries(arr: IntArray, queries: Array<IntArray>): IntArray {
        val result = IntArray(queries.size) { 0 }
        for (i in queries.indices) {
            for (query in queries[i][0]..queries[i][1])
                result[i] = result[i] xor arr[query]
        }
        return result
    }

    @Test
    fun study() {
        var x = 100
        var y = 123
        // 保持y的原值不变
        println(x xor y xor x)
        // 交换x和y
        x = x xor y
        y = x xor y
        x = x xor y
        print("x:$x, y:$y")
    }

    @Test
    fun test0() {
        val queries = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(0, 3),
            intArrayOf(3, 3)
        )
        assert(intArrayOf(2, 7, 14, 8).contentEquals(xorQueries(intArrayOf(1, 3, 4, 8), queries)))
    }

    @Test
    fun test1() {
        val queries = arrayOf(
            intArrayOf(2, 3),
            intArrayOf(1, 3),
            intArrayOf(0, 0),
            intArrayOf(0, 3)
        )
        assert(intArrayOf(8, 0, 4, 4).contentEquals(xorQueries(intArrayOf(4, 8, 2, 10), queries)))
    }

    @Test
    fun test2() {
        val queries = arrayOf(
            intArrayOf(0, 0),
            intArrayOf(0, 0),
            intArrayOf(0, 0)
        )
        assert(intArrayOf(16, 16, 16).contentEquals(xorQueries(intArrayOf(16), queries)))
    }
}