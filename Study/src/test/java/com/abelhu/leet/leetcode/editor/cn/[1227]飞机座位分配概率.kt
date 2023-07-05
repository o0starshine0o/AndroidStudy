import org.junit.Assert
import org.junit.Test

//有 n 位乘客即将登机，飞机正好有 n 个座位。第一位乘客的票丢了，他随便选了一个座位坐下。
//
// 剩下的乘客将会： 
//
// 
// 如果他们自己的座位还空着，就坐到自己的座位上， 
// 当他们自己的座位被占用时，随机选择其他座位 
// 
//
// 第 n 位乘客坐在自己的座位上的概率是多少？ 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 1
//输出：1.00000
//解释：第一个人只会坐在自己的位置上。 
//
// 示例 2： 
//
// 
//输入: n = 2
//输出: 0.50000
//解释：在第一个人选好座位坐下后，第二个人坐在自己的座位上的概率是 0.5。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 10^5 
// 
//
// Related Topics 脑筋急转弯 数学 动态规划 概率与统计 👍 118 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1227 {
    private fun nthPersonGetsNthSeat(n: Int): Double {
        // n == 1 时, dp[1] = 1/1
        // n == 2 时, dp[2] = 1/2 * dp[1], 第0个人坐0号位
        // n == 3 时, dp[3] = 1/3 * dp[1] + 1/3 * dp[2], 第1个人坐1号位+第1个人坐2号位*(第2个人坐相对正确位置)
        // n == 4 时, dp[4] = 1/4 * dp[1] + 1/4 * dp[2] + 1/4 * dp[3]
        // dp[i] 代表了第i个人, 坐在**相对正确**位置的概率
        if (n == 1) return 1.0

        // 用空间换区思路清晰
        val dp = DoubleArray(n + 1)
        dp[1] = 1.0
        // 只使用dp会超时, 使用ap保存sum(dp[i])
        val ap = DoubleArray(n + 1)
        ap[1] = dp[1]

        // 利用dp和ap进行动归运算
        for (i in 2..n) {
            dp[i] = 1.0 / i * ap[i-1]
            ap[i] = dp[i] + ap[i -1]
        }

        return dp[n]
    }
    private fun nthPersonGetsNthSeat2(n: Int): Double {
        // n == 1 时, dp[1] = 1/1
        // n == 2 时, dp[2] = 1/2 * dp[1], 第0个人坐0号位
        // n == 3 时, dp[3] = 1/3 * dp[1] + 1/3 * dp[2], 第1个人坐1号位+第1个人坐2号位*(第2个人坐相对正确位置)
        // n == 4 时, dp[4] = 1/4 * dp[1] + 1/4 * dp[2] + 1/4 * dp[3]
        // dp[i] 代表了第i个人, 坐在**相对正确**位置的概率
        if (n == 1) return 1.0

        // 用空间换区思路清晰
        val dp = DoubleArray(n + 1)
        dp[1] = 1.0

        // 利用一个dp进行动归运算
        // 注意! 使用双循环会导致超时, 再使用一个ap表空间换时间
        for (i in 2..n) {
            for (k in 1 until i) {
                dp[i] += (1.0 / i * dp[k])
            }
        }

        return dp[n]
    }

    @Test
    fun test0() {
        // 除了1之外, 全部都是0.5
        for (i in 2..10) {
            Assert.assertEquals(0.5, nthPersonGetsNthSeat(i), 0.0001)
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
