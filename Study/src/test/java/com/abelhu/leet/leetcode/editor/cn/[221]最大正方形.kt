import org.junit.Test
import kotlin.math.max
import kotlin.math.min

//在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
//
// 
//
// 示例 1： 
// 
// 
//输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"]
//,["1","0","0","1","0"]]
//输出：4
// 
//
// 示例 2： 
// 
// 
//输入：matrix = [["0","1"],["1","0"]]
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：matrix = [["0"]]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 300 
// matrix[i][j] 为 '0' 或 '1' 
// 
//
// Related Topics 数组 动态规划 矩阵 👍 1672 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution221 {
    // 用一个表保存计算结果, 避免反复运算
    private lateinit var dp: Array<IntArray>
    private var result = 0
    fun maximalSquare(matrix: Array<CharArray>): Int {
        // 初始化时把0的位置直接标记上, 免得反复运算
        dp = Array(matrix.size) { IntArray(matrix[0].size) { -1 } }
        maximalSquare(matrix, 0, 0)
        return result * result
    }

    /**
     * @return 最大边长
     */
    private fun maximalSquare(matrix: Array<CharArray>, row: Int, column: Int): Int {
        // println("maximalSquare($row, $column)")
        // 当前节点是否越界
        if (row >= matrix.size) return 0
        if (column >= matrix[0].size) return 0

        // 判断是否已经计算过
        if (dp[row][column] != -1) return dp[row][column]

        // 取临接的最大值
        val rowOne = maximalSquare(matrix, row + 1, column) // 下侧
        val columnOne = maximalSquare(matrix, row, column + 1) // 右侧
        val columnRowOne = maximalSquare(matrix, row + 1, column + 1) // 右下侧

        // 计算当前值, 为1则取最小值+1, 为0则取0
        val current = if (matrix[row][column] == '1') min(min(columnOne, rowOne), columnRowOne) + 1 else 0
        // 更新最大值
        result = max(result, current)
        // 保存当前节点
        dp[row][column] = current //.apply { println("resultTab[$row][$column]: $this") }

        return current
    }

    @Test
    fun test0() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('1', '0', '1', '0', '0'),
                charArrayOf('1', '0', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1', '1'),
                charArrayOf('1', '0', '0', '1', '0')
            )
        ).apply { print("test0: $result") }
        assert(4 == result)
    }

    @Test
    fun test1() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('0', '1'),
                charArrayOf('1', '0')
            )
        ).apply { println("test1: $result") }
        assert(1 == result)
    }

    @Test
    fun test2() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('0')
            )
        ).apply { println("test2: $result") }
        assert(0 == result)
    }

    @Test
    fun test3() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1')
            )
        ).apply { println("test3: $result") }
        assert(16 == result)
    }

    @Test
    fun test4() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '0')
            )
        ).apply { println("test4: $result") }
        assert(9 == result)
    }

    @Test
    fun test5() {
        val solution = Solution221()
        val result = solution.maximalSquare(
            arrayOf(
                charArrayOf('0', '0', '1', '1', '1'),
                charArrayOf('0', '0', '1', '1', '1'),
                charArrayOf('0', '1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1', '1'),
                charArrayOf('1', '1', '1', '1', '1')
            )
        ).apply { println("test5: $result") }
        assert(9 == result)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
