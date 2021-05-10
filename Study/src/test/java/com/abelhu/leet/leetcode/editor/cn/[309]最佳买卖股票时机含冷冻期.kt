import org.junit.Test

//给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。
//
// 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）: 
//
// 
// 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
// 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。 
// 
//
// 示例: 
//
// 输入: [1,2,3,0,2]
//输出: 3 
//解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出] 
// Related Topics 动态规划 
// 👍 766 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution309 {

    fun maxProfit(prices: IntArray, k: Int = prices.size): Int {
        // 初始化一个二维数组,保存[开始位置,K次交易的最大利润]
        val dp = Array(prices.size) { IntArray(k) { -1 } }
        // 返回结果
        return maxProfitSub(prices, 0, dp, k)
    }

    // 在[start, end]区间内,完成k次交易获取的最大利润
    fun maxProfitSub(prices: IntArray, start: Int, dp: Array<IntArray>, k: Int = 2): Int {
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
            result = kotlin.math.max(result, price - min + maxProfitSub(prices, i + 2, dp, k - 1))
        }
        // 保存dp
        dp[start][k - 1] = result
        // 返回计算的结果:在[start, end]区间内,完成k次交易获取的最大利润
        return result
    }

    @Test
    fun test0() {
        assert(3 == maxProfit(intArrayOf(1,2,3,0,2)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
