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
