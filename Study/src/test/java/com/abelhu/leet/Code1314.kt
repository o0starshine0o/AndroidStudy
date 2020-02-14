package com.abelhu.leet

import org.junit.Test
import kotlin.math.max
import kotlin.math.min

class Code1314 {
    fun matrixBlockSum(mat: Array<IntArray>, k: Int): Array<IntArray> {
        // dp算法直接算出新的矩阵和
        for (i in mat.indices) {
            var sum = 0
            for (j in mat[i].indices) {
                sum += mat[i][j]
                mat[i][j] = when (i) {
                    0 -> sum
                    else -> sum + mat[i - 1][j]
                }
            }
        }
        // 结果矩阵
        val result = Array(mat.size) { IntArray(mat[0].size) { 0 } }
        for (i in result.indices) {
            for (j in result[i].indices) {
                // 计算边界
                val rowMin = max(0, i - k)
                val rowMax = min(result.size - 1, i + k)
                val colMin = max(0, j - k)
                val colMax = min(result[i].size - 1, j + k)
                // 计算矩阵和
                if (rowMin == 0 && colMin == 0) result[i][j] = mat[rowMax][colMax]
                else if (rowMin == 0) result[i][j] = mat[rowMax][colMax] - mat[rowMax][colMin - 1]
                else if (colMin == 0) result[i][j] = mat[rowMax][colMax] - mat[rowMin - 1][colMax]
                else result[i][j] = mat[rowMax][colMax] - mat[rowMin - 1][colMax] - mat[rowMax][colMin - 1] + mat[rowMin - 1][colMin - 1]
            }
        }
        // 结果
        return result
    }

    @Test
    fun test0() {
        val result = matrixBlockSum(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 9)
            ), 1
        )
        for (array in result) {
            for (i in array) {
                print("$i,")
            }
            println()
        }
    }

    @Test
    fun test1() {
        val result = matrixBlockSum(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 9)
            ), 2
        )
        for (array in result) {
            for (i in array) {
                print("$i,")
            }
            println()
        }
    }
}