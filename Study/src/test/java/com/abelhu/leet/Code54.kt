package com.abelhu.leet

import org.junit.Test

class Code54 {
    // 边界
    private var row = 0
    private var col = 0
    // 指针
    private var i = 0
    private var j = 0
    // 方向 1:right, 2:down, 3:left, 4:up
    private var direction = 1
    // 数组
    private lateinit var matrix: Array<IntArray>

    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        if (matrix.isEmpty()) return MutableList(0) { 0 }
        row = matrix.size
        col = matrix[0].size
        this.matrix = matrix
        val size = row * col
        val result = MutableList(size) { 0 }
        for (index in 0 until size) {
            result[index] = matrix[i][j]
            matrix[i][j] = Int.MIN_VALUE
            if (index + 1 < size) nextPoint()
        }
        return result
    }

    private fun nextPoint() {
        when (direction) {
            1 -> {
                if (j + 1 >= col || matrix[i][j + 1] == Int.MIN_VALUE) {
                    direction = 2
                    nextPoint()
                } else {
                    j++
                }
            }
            2 -> {
                if (i + 1 >= row || matrix[i + 1][j] == Int.MIN_VALUE) {
                    direction = 3
                    nextPoint()
                } else {
                    i++
                }
            }
            3 -> {
                if (j - 1 < 0 || matrix[i][j - 1] == Int.MIN_VALUE) {
                    direction = 4
                    nextPoint()
                } else {
                    j--
                }
            }
            4 -> {
                if (i - 1 < 0 || matrix[i - 1][j] == Int.MIN_VALUE) {
                    direction = 1
                    nextPoint()
                } else {
                    i--
                }
            }
        }
    }


    @Test
    fun test0() {
        assert(
            listOf(1, 2, 3, 6, 9, 8, 7, 4, 5).containsAll(
                spiralOrder(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(1, 2, 3),
                        intArrayOf(1, 2, 3)
                    )
                )
            )
        )
    }

    @Test
    fun test1() {
        assert(
            listOf(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7).containsAll(
                spiralOrder(
                    arrayOf(
                        intArrayOf(1, 2, 3, 4),
                        intArrayOf(5, 6, 7, 8),
                        intArrayOf(9, 10, 11, 12)
                    )
                )
            )
        )
    }
}