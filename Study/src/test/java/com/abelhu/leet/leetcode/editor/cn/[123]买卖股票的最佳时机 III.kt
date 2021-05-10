import org.junit.Test

//给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。 
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
//
// 
//
// 示例 1: 
//
// 
//输入：prices = [3,3,5,0,0,3,1,4]
//输出：6
//解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
//     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。 
//
// 示例 2： 
//
// 
//输入：prices = [1,2,3,4,5]
//输出：4
//解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
//     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
//     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
// 
//
// 示例 3： 
//
// 
//输入：prices = [7,6,4,3,1] 
//输出：0 
//解释：在这个情况下, 没有交易完成, 所以最大利润为 0。 
//
// 示例 4： 
//
// 
//输入：prices = [1]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 1 <= prices.length <= 105 
// 0 <= prices[i] <= 105 
// 
// Related Topics 数组 动态规划 
// 👍 763 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution123 {

    fun maxProfit(prices: IntArray, k: Int = 2): Int {
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
            result = kotlin.math.max(result, price - min + maxProfitSub(prices, i + 1, dp, k - 1))
        }
        // 保存dp
        dp[start][k - 1] = result
        // 返回计算的结果:在[start, end]区间内,完成k次交易获取的最大利润
        return result
    }

    @Test
    fun test0() {
        assert(6 == maxProfit(intArrayOf(3, 3, 5, 0, 0, 3, 1, 4)))
    }

    @Test
    fun test1() {
        assert(4 == maxProfit(intArrayOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun test2() {
        assert(0 == maxProfit(intArrayOf(7, 6, 4, 3, 1)))
    }

    @Test
    fun test3() {
        assert(0 == maxProfit(intArrayOf(1)))
    }

    @Test
    fun test4() {
        // 3, 5, 7
        // 6, 7
        assert(13 == maxProfit(intArrayOf(1, 2, 4, 2, 5, 7, 2, 4, 9, 0)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
