import com.abelhu.leet.utils.log
import org.junit.Assert
import org.junit.Test

//给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
//
// 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。 
//
// 返回获得利润的最大值。 
//
// 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。 
//
// 示例 1: 
//
// 输入: prices = [1, 3, 2, 8, 4, 9], fee = 2
//输出: 8
//解释: 能够达到的最大利润:  
//在此处买入 prices[0] = 1
//在此处卖出 prices[3] = 8
//在此处买入 prices[4] = 4
//在此处卖出 prices[5] = 9
//总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8. 
//
// 注意: 
//
// 
// 0 < prices.length <= 50000. 
// 0 < prices[i] < 50000. 
// 0 <= fee < 50000. 
// 
// Related Topics 贪心算法 数组 动态规划 
// 👍 469 👎 0

class Solution7142 {
    private fun maxProfit(prices: IntArray, fee: Int): Int {
        val size = prices.size
        // 构建一个n*2的矩阵
        // dp[i][0] 代表当天如果持有股票的总利润
        // dp[i][1] 代表当天如果抛售股票的总利润
        // 则状态转移方程为:
        // dp[i][0] = max{
        //     dp[i-1][0], // 如果前一天持有, 那么继续持有就行
        //     dp[i-1][1] - prices[i] // 如果前一天没有, 那么需要以当天价格买入
        // }
        // dp[i][1] = max{
        //     dp[i-1][0] + prices[i] - fee, // 如果前一天持有, 那么此时需要抛售, 并且支付费用
        //     dp[i-1][1] // 如果前一天没有, 那么继续观望就行
        // }
        // 最终需要求得dp[i][1]
        val dp = Array(size) { IntArray(2) }
        // 初始条件
        dp[0][0] = -prices[0]
        dp[0][1] = 0
        // 动规求解
        for (i in 1 until  size) {
            dp[i][0] = kotlin.math.max(dp[i-1][0], dp[i-1][1] - prices[i])
            dp[i][1] = kotlin.math.max(dp[i-1][0] + prices[i] - fee, dp[i-1][1])
        }
        return dp[size-1][1]
    }

    private fun maxProfit2(prices: IntArray, fee: Int): Int {
        val size = prices.size
        // 构建一个n*n的dp矩阵
        // 其中 dp[i][j] 代表从i点买入j点卖出时能够获得的最大利润(已经算过fee了)
        // 假设 从i点到j点可以完成1次(i点买j点卖)或者2次交易(类似子序列)
        // 则状态转移方程为:
        // dp[i][j] = max{
        //     // 注意! dp[i][k]和dp[k+1][j]已经算过fee了, 不要重复计算
        //     2次交易: max(0, dp[i][k]) + max(0, dp[k+1][j]), 其中i<=k<j
        //     1次交易: prices[j] - prices[i] - fee
        //     0次交易: 0
        // }
        // 观察可得, 为了得到dp[0][n]的值, 需要其上方和右方的数据
        // 所以需要对角线序遍历数组
        val dp = Array(size) { IntArray(size) }

        // 初始宠[0,1]开始, 因为dp[i][i]都为0
        var i = 0
        var j = 1
        // 用一个y来暂存层序的起点
        var y = 1
        // 如果层序的起点越界了, 代表矩阵下方计算完成
        while (y < size) {
            // 2次交易
            var max = Int.MIN_VALUE
            for (k in i until j) {
                val profit = kotlin.math.max(0, dp[i][k]) + kotlin.math.max(0, dp[k + 1][j])
                max = kotlin.math.max(max, profit)
            }
            // 1次交易
            dp[i][j] = kotlin.math.max(max, prices[j] - prices[i] - fee)
            // 0次交易
            dp[i][j] = kotlin.math.max(dp[i][j], 0).log { "dp[$i][$j] = $it" }

            // 标准对角线序
            i++
            j++

            // 会越界了, 重新到层序的下一层起点
            if (j >= size) {
                y++
                i = 0
                j = y
            }
        }

        return dp[0][size - 1]
    }

    @Test
    fun test0() {
        Assert.assertEquals(8, maxProfit(intArrayOf(1, 3, 2, 8, 4, 9), 2))
    }

    @Test
    fun test1() {
        Assert.assertEquals(6, maxProfit(intArrayOf(1, 3, 7, 5, 10, 3), 3))
    }
}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution714 {
    fun maxProfit(prices: IntArray, fee: Int, k: Int = prices.size): Int {
        // 初始化一个二维数组,保存[开始位置,K次交易的最大利润]
        val dp = Array(prices.size) { IntArray(k) { -1 } }
        // 返回结果
        return maxProfitSub(prices, 0, fee, dp, k)
    }

    // 在[start, end]区间内,完成k次交易获取的最大利润
    fun maxProfitSub(prices: IntArray, start: Int, fee: Int, dp: Array<IntArray>, k: Int = 2): Int {
        // 结束条件
        if (start >= prices.size) return 0
        if (k <= 0) return 0
        // 查询dp
        if (dp[start][k - 1] >= 0) return dp[start][k - 1]
        // 按照121的方法,计算一次交易能获取到的最大利润
        // 在此基础上进行改进,还需要额外加上剩余空间k-1次交易能获取到的利润
        var min = Int.MAX_VALUE
        var result = 0
        for (i in start until prices.size) {
            val price = prices[i]
            // 先确定当前最小值
            min = kotlin.math.min(min, price)
            // 在当前最小值的基础上再进行计算
            // 目前得到的利润+剩余空间可以获取的利润
            result = kotlin.math.max(result, price - min + maxProfitSub(prices, i + 1, fee, dp, k - 1) - fee)
        }
        // 保存dp
        dp[start][k - 1] = result
        // 返回计算的结果:在[start, end]区间内,完成k次交易获取的最大利润
        return result
    }

    @Test
    fun test0() {
        assert(8 == maxProfit(intArrayOf(1, 3, 2, 8, 4, 9), 2))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
