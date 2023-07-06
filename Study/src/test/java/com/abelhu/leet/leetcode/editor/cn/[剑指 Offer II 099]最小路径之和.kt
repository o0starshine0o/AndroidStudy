import org.junit.Assert
import org.junit.Test

//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
// 说明：一个机器人每次只能向下或者向右移动一步。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//输出：7
//解释：因为路径 1→3→1→1→1 的总和最小。
// 
//
// 示例 2： 
//
// 
//输入：grid = [[1,2,3],[4,5,6]]
//输出：12
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 200 
// 0 <= grid[i][j] <= 100 
// 
//
// 
//
// 
// 注意：本题与主站 64 题相同： https://leetcode-cn.com/problems/minimum-path-sum/ 
//
// Related Topics 数组 动态规划 矩阵 👍 52 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution099 {
    private fun minPathSum(grid: Array<IntArray>): Int {
        val m = grid.size
        val n = grid.first().size
        // 动规, 使用dp
        // 增加一行一列, 避免复杂的边界条件
        // dp[0][j] = dp[i][0] = Int.Max_Value
        // dp[i][j] 代表到达(i,j)的最短路径
        // 则状态转移方程为:
        // dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i-1][[j-1], 其中1<=i<=m, 1<=j<=n
        // 初始条件:
        // dp[1][1] = grid[0][0]
        // 答案为:
        // dp[m][n]
        val dp = Array(m + 1) { IntArray(n + 1) { Int.MAX_VALUE } }
        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] = when {
                    i == 1 && j == 1 -> grid[0][0]
                    else -> kotlin.math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i -1][j-1]
                }
            }
        }
        return dp[m][n]
    }

    @Test
    fun test0() {
        Assert.assertEquals(7, minPathSum(arrayOf(intArrayOf(1, 3, 1), intArrayOf(1, 5, 1), intArrayOf(4, 2, 1))))
    }

    @Test
    fun test1() {
        Assert.assertEquals(12, minPathSum(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
