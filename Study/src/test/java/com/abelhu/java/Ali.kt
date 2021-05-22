package com.abelhu.java

import org.junit.Test

class Ali {

    fun island(map: Array<IntArray>): Int {
        var max = Int.MIN_VALUE
        // 循环遍历map,对每一个item进行判断
        for ((i, line) in map.withIndex()) {
            for ((j, square) in line.withIndex()) {
                // 未访问过,且为陆地的单元格进入面积判定
                if (square > 0) {
                    val size = getSize(i, j, map, Array(map.size) { i -> BooleanArray(map[i].size) { false } })
                    max = kotlin.math.max(max, size)
                }
            }
        }
        return max
    }

    private fun getSize(i: Int, j: Int, map: Array<IntArray>, visite: Array<BooleanArray>): Int {
        // 异常: 越界|为空|本次遍历中已访问过的
        if (i < 0 || j < 0 || i >= map.size || j >= map[i].size || map[i][j] <= 0 || visite[i][j]) return 0
        // 标记为已访问
        visite[i][j] = true
        // 获取所有的相邻岛屿面积
        return 1 + getSize(i + 1, j, map, visite) + getSize(i - 1, j, map, visite) + getSize(i, j + 1, map, visite) + getSize(i, j - 1, map, visite)

    }

    @Test
    fun test0() {
        assert(4 == island(arrayOf(intArrayOf(1, 1, 0, 0, 0), intArrayOf(1, 1, 0, 0, 0), intArrayOf(0, 0, 1, 0, 0), intArrayOf(0, 0, 0, 1, 1))))
    }
}