import org.junit.Assert
import org.junit.Test

//一个机器人位于一个
// m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。 
//
// 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。 
//
// 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？ 
//
// 网格中的障碍物和空位置分别用 1 和 0 来表示。 
//
// 
//
// 示例 1： 
// 
// 
//输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//输出：2
//解释：3x3 网格的正中间有一个障碍物。
//从左上角到右下角一共有 2 条不同的路径：
//1. 向右 -> 向右 -> 向下 -> 向下
//2. 向下 -> 向下 -> 向右 -> 向右
// 
//
// 示例 2： 
// 
// 
//输入：obstacleGrid = [[0,1],[0,0]]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// m == obstacleGrid.length 
// n == obstacleGrid[i].length 
// 1 <= m, n <= 100 
// obstacleGrid[i][j] 为 0 或 1 
// 
//
// Related Topics 数组 动态规划 矩阵 👍 1078 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution63 {
    private fun uniquePathsWithObstacles(obstacleGrid: Array<IntArray>): Int {
        // 定义一个m*n的dp
        // dp[i][j] 代表从起点到(i,j)的可行解数量
        // 状态转移方程为:
        // 注意! obstacleGrid[i][j]如果为1则dp[i][j]就应该为0
        // dp[i][j] = if(i == 0 && j == 0) 1 - obstacleGrid[i][j] // 左上角起点
        // else if (i - 1 < 0) 1 - obstacleGrid[i][j-1] // 第0行
        // else if (j - 1 < 0) 1 - obstacleGrid[i-1][j] // 第0列
        // else (1 - obstacleGrid[i-1][j]) + (1 - obstacleGrid[i][j-1]) // 其余位置
        // dp[n-1][n-1] 即为最终解

        val m = obstacleGrid.size
        val n = obstacleGrid.first().size
        val dp = Array(m) { IntArray(n) }

        for (i in 0 until m) {
            for (j in 0 until n) {
                dp[i][j] = when {
                    // 本身就是障碍, 无法到达
                    obstacleGrid[i][j] == 1 -> 0
                    // 入口
                    i == 0 && j == 0 -> 1 - obstacleGrid[i][j]
                    // 第0行
                    i == 0 -> dp[i][j - 1]
                    // 上一行有障碍
                    // 注意! 这里尤其需要判断j==0的情况,
                    obstacleGrid[i - 1][j] == 1 -> if (j == 0) 0 else dp[i][j - 1]
                    // 第0列
                    j == 0 -> dp[i - 1][j]
                    // 上一列有障碍
                    obstacleGrid[i][j - 1] == 1 -> dp[i - 1][j]
                    // 其他地方
                    else -> dp[i][j - 1] + dp[i - 1][j]
                }
            }
        }

        return dp[m - 1][n - 1]
    }

    @Test
    fun test0() {
        Assert.assertEquals(2, uniquePathsWithObstacles(arrayOf(intArrayOf(0, 0, 0), intArrayOf(0, 1, 0), intArrayOf(0, 0, 0))))
    }

    @Test
    fun test1() {
        Assert.assertEquals(1, uniquePathsWithObstacles(arrayOf(intArrayOf(0, 1), intArrayOf(0, 0))))
    }

    @Test
    fun test2() {
        Assert.assertEquals(0, uniquePathsWithObstacles(arrayOf(intArrayOf(0, 0), intArrayOf(0, 1))))
    }

    @Test
    fun test3() {
        Assert.assertEquals(0, uniquePathsWithObstacles(arrayOf(intArrayOf(1, 0), intArrayOf(0, 0))))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
