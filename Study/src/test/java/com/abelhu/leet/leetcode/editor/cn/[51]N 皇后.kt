import org.junit.Test

//n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。 
//
// 
// 
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：[["Q"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 9 
// 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。 
// 
// 
// 
// Related Topics 回溯算法 
// 👍 833 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution51 {
    fun solveNQueens(n: Int): List<List<String>> {
        // 先构造一个二维矩阵,保存结果
        val matrix = Array(n) { CharArray(n) { '.' } }
        // 最终结果
        val result = mutableListOf<List<String>>()
        // 进入回溯算法
        backTrack(n, 0, matrix, result)
        return result

    }

    private fun backTrack(n: Int, row: Int, matrix: Array<CharArray>, result: MutableList<List<String>>) {
        // 结束回溯的条件
        if (row >= n) {
            result.add(matrix.toList().map { line -> line.joinToString(separator = "") })
            return
        }
        for (col in 0 until n) {
            // 排除错误的选项
            if (!isValid(row, col, matrix)) continue
            // 做出选择
            matrix[row][col] = 'Q'
            // 进如下一层
            backTrack(n, row + 1, matrix, result)
            // 回溯
            matrix[row][col] = '.'
        }

    }

    private fun isValid(row: Int, col: Int, matrix: Array<CharArray>): Boolean {
        // 检查列
        (0 until row).forEach { if (matrix[it][col] == 'Q') return false }
        // 检查行
        (0 until col).forEach { if (matrix[row][it] == 'Q') return false }
        // 检查左上斜线
        for (num in 1..row) {
            if (col - num < 0) break
            if (matrix[row - num][col - num] == 'Q') return false
        }
        // 检查右上斜线
        for (num in 1..row) {
            if (col + num >= matrix.size) break
            if (matrix[row - num][col + num] == 'Q') return false
        }
        return true
    }

    @Test
    fun test() {
        print(solveNQueens(4))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
