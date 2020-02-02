package com.abelhu.leet

import org.junit.Test

class Code1184 {
    fun distanceBetweenBusStops(distance: IntArray, start: Int, destination: Int): Int {
        // 保证s一直小于e
        val s = if (start < destination) start else destination
        val e = if (start < destination) destination else start
        // 计算正序
        var order = 0
        for (i in s until e) order += distance[i % distance.size]
        // 计算逆序
        var deOrder = 0
        for (i in e until distance.size + s) deOrder += distance[i % distance.size]
        // 返回最小值
        return kotlin.math.min(order, deOrder)
    }

    @Test
    fun test0() {
        assert(1 == distanceBetweenBusStops(intArrayOf(1, 2, 3, 4), 0, 1))
    }

    @Test
    fun test1() {
        assert(3 == distanceBetweenBusStops(intArrayOf(1, 2, 3, 4), 0, 2))
    }

    @Test
    fun test2() {
        assert(4 == distanceBetweenBusStops(intArrayOf(1, 2, 3, 4), 0, 3))
    }
}