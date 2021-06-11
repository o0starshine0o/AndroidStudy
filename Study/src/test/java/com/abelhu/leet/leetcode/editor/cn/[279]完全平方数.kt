import org.junit.Test

//给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
//
// 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。 
//
// 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
// 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 12
//输出：3 
//解释：12 = 4 + 4 + 4 
//
// 示例 2： 
//
// 
//输入：n = 13
//输出：2
//解释：13 = 4 + 9 
// 
//
// 提示： 
//
// 
// 1 <= n <= 104 
// 
// Related Topics 广度优先搜索 数学 动态规划 
// 👍 944 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution279 {
    fun numSquares(n: Int): Int {
        val dp = IntArray(n + 1) { n }
        dp[0] = 0
        for (i in 0..n) {
            var j = 1
            while (i - j * j >= 0) {
                dp[i] = kotlin.math.min(dp[i], 1 + dp[i - j * j])
                j++
            }
        }
        return dp[n]
    }

    @Test
    fun test0() {
        assert(3 == numSquares(12))
    }

    @Test
    fun test1() {
        assert(2 == numSquares(13))
    }

    @Test
    fun test2() {
        assert(3 == numSquares(14))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
