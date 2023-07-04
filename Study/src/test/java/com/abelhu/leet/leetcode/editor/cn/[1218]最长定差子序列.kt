import org.junit.Assert
import org.junit.Test

//给你一个整数数组 arr 和一个整数 difference，请你找出并返回 arr 中最长等差子序列的长度，该子序列中相邻元素之间的差等于
//difference 。 
//
// 子序列 是指在不改变其余元素顺序的情况下，通过删除一些元素或不删除任何元素而从 arr 派生出来的序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：arr = [1,2,3,4], difference = 1
//输出：4
//解释：最长的等差子序列是 [1,2,3,4]。 
//
// 示例 2： 
//
// 
//输入：arr = [1,3,5,7], difference = 1
//输出：1
//解释：最长的等差子序列是任意单个元素。
// 
//
// 示例 3： 
//
// 
//输入：arr = [1,5,7,8,5,3,4,2,1], difference = -2
//输出：4
//解释：最长的等差子序列是 [7,5,3,1]。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= arr.length <= 10⁵ 
// -10⁴ <= arr[i], difference <= 10⁴ 
// 
//
// Related Topics 数组 哈希表 动态规划 👍 245 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1218 {
    fun longestSubsequence(arr: IntArray, difference: Int): Int {
        // dp[i] 保存以 arr[i]为结尾的的最长等差子序列长度
        val dp = IntArray(arr.size) { 1 }
        var max = 1
        for (i in 1 until arr.size) {
            for (k in i - 1 downTo 0) {
                if (arr[k] + difference == arr[i]) {
                    dp[i] = dp[k] + 1
                    max = kotlin.math.max(max, dp[i])
                    break
                }
            }
            if (dp[i] > 1) continue
        }
        return max
    }

    fun longestSubsequence2(arr: IntArray, difference: Int): Int {
        val size = arr.size
        // 使用一个dp矩阵保存最长子序列
        // dp[i][j]表示从i到j, 最长的等差子序列, 以及此序列的最后一位数字
        // (maxLength, num)
        // 初始化dp[i][i] = 1 to arr[i]
        val dp = Array(size) { i -> Array(size) { j -> (if (i == j) 1 else 0) to arr[i] } }
        // 工具公式推演, 每一个dp[i][j]依赖于其上层的三角
        // 按照层序遍历矩阵下半部分
        // 公式: dp[i][j] = max{dp[m][n], 其中0<=m<=n<j} + 0/1
        // 注意! 对矩阵的遍历还需要加强
        for (j in 0 until size) {
            for (i in 0..j) {
                val start = kotlin.math.min(i, j)
                // 每一个item都需要遍历其上的三角形区域
                var max = dp[j][j]
                for (n in start until j) {
                    for (m in start..n) {
                        // 注意, 这里仅仅是一个compare, 而不是max
                        var compare = dp[m][n]
                        val isNext = compare.second + difference == dp[j][j].second
                        if (isNext) {
                            compare = dp[m][n].first + 1 to dp[j][j].second
                        }
                        max = if (compare.first > max.first) compare else max
                    }
                }
                // 遍历完上层区域, 把最大值赋给当前数
//                dp[i][j] = max.log { "dp[$i][$j] = $it" }
                dp[i][j] = max
            }
        }
        // 左下角的单元格就是所求答案
        return dp[0][size - 1].first
    }

    @Test
    fun test0() {
        Assert.assertEquals(4, longestSubsequence(listOf(1, 2, 3, 4).toIntArray(), 1))
    }

    @Test
    fun test1() {
        Assert.assertEquals(1, longestSubsequence(listOf(1, 3, 5, 7).toIntArray(), 1))
    }

    @Test
    fun test2() {
        Assert.assertEquals(4, longestSubsequence(listOf(1, 5, 7, 8, 5, 3, 4, 2, 1).toIntArray(), -2))
    }

    @Test
    fun test3() {
        Assert.assertEquals(2, longestSubsequence(listOf(3, 4, -3, -2, -4).toIntArray(), -5))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
