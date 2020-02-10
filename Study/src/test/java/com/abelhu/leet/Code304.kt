package com.abelhu.leet

import org.junit.Test

class Code304 {
    class NumMatrix(private val matrix: Array<IntArray>) {
        init {
            for (i in matrix.indices) {
                var sum = 0
                for (j in matrix[i].indices) {
                    sum += matrix[i][j]
                    matrix[i][j] = if (i == 0) sum else matrix[i - 1][j] + sum
                }
            }
        }

        fun sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int {
            if (row1 == 0 && col1 == 0) return matrix[row2][col2]
            else if (row1 == 0) return matrix[row2][col2] - matrix[row2][col1 - 1]
            else if (col1 == 0) return matrix[row2][col2] - matrix[row1 - 1][col2]
            return matrix[row2][col2] + matrix[row1 - 1][col1 - 1] - matrix[row2][col1 - 1] - matrix[row1 - 1][col2]
        }
    }

    @Test
    fun test0() {
        val matrix = NumMatrix(
            arrayOf(
                intArrayOf(3, 0, 1, 4, 2),
                intArrayOf(5, 6, 3, 2, 1),
                intArrayOf(1, 2, 0, 1, 5),
                intArrayOf(4, 1, 0, 1, 7),
                intArrayOf(1, 0, 3, 0, 5)
            )
        )

        assert(8 == matrix.sumRegion(2, 1, 4, 3))
        assert(11 == matrix.sumRegion(1, 1, 2, 2))
        assert(12 == matrix.sumRegion(1, 2, 2, 4))
    }
}