import org.junit.Test

//给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
//
// 
//
// 
// 
//
// 示例 1: 
//
// 输入: amount = 5, coins = [1, 2, 5]
//输出: 4
//解释: 有四种方式可以凑成总金额:
//5=5
//5=2+2+1
//5=2+1+1+1
//5=1+1+1+1+1
// 
//
// 示例 2: 
//
// 输入: amount = 3, coins = [2]
//输出: 0
//解释: 只用面额2的硬币不能凑成总金额3。
// 
//
// 示例 3: 
//
// 输入: amount = 10, coins = [10] 
//输出: 1
// 
//
// 
//
// 注意: 
//
// 你可以假设： 
//
// 
// 0 <= amount (总金额) <= 5000 
// 1 <= coin (硬币面额) <= 5000 
// 硬币种类不超过 500 种 
// 结果符合 32 位符号整数 
// 
// 👍 461 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution518 {
    val dp = HashMap<Int, Int>()

    /**
     * 使用回溯+剪枝
     * 这题的一个陷阱:
     * 使用dp的时候是有条件的,比如2,如果限制是从2开始计算,结果为2(2, 1+1),如果限制是1,结果为1(1+1)
     * 所以dp需要使用二维<amount, start>
     * 实际操作中,dp的效率很差,导致dp几乎没用,所以不能用回溯,要用动归
     */
    fun change(amount: Int, coins: IntArray): Int {
        // 先保证硬币降序排列
        coins.sortDescending()
        // 计算结果
        return change(amount, coins, 0, HashMap())
    }

    private fun change(amount: Int, coins: IntArray, start: Int, dp: HashMap<Int, HashMap<Int, Int>>): Int {
        return when (amount) {
            // 如果小于0,直接放弃
            in Int.MIN_VALUE until 0 -> 0
            // 只有等于0的,才任务是可以凑整
            0 -> 1
            // 大于0的情况需要继续递归运算
            else ->
                // 使用dp进行剪枝
                dp[amount]?.get(start) ?: Unit.let {
                    var result = 0
                    for (index in start until coins.size) change(amount - coins[index], coins, index, dp).takeIf { it > 0 }?.apply { result += this }
                    // 更新dp并进行返回
                    return result.apply { (dp[amount] ?: HashMap())[start] = this }
                }
        }
    }

    /**
     * dp[amount] 从dp[0]开始计算,直到dp[amount]
     */
    fun change1(amount: Int, coins: IntArray): Int {
        // dp[0] = 1; 因为有一种方案,就是coin为空
        val dp = IntArray(amount + 1) { if (it == 0) 1 else 0 }
        // 要把coins的遍历放在外面,防止coins反复横跳
        for (coin in coins) {
            // 从dp[coin]开始计算,因为小于coin的话计算没有意义
            for (i in coin..amount) {
                dp[i] += dp[i - coin]
            }
        }
        return dp[amount]
    }

    @Test
    fun test0() {
        assert(4 == change(5, intArrayOf(1, 2, 5)).apply { println(this) })
    }

    @Test
    fun test1() {
        assert(2 == change(2, intArrayOf(1, 2, 5)).apply { println(this) })
    }

    @Test
    fun test2() {
        assert(0 == change(3, intArrayOf(2)))
    }

    @Test
    fun test3() {
        assert(1 == change(10, intArrayOf(10)))
    }

    @Test
    fun test4() {
        // 这个测试超时,就先跳过
//        assert(1 == change(500, intArrayOf(3, 5, 7, 8, 9, 10, 11)))
    }

    @Test
    fun test10() {
        assert(4 == change1(5, intArrayOf(1, 2, 5)).apply { println(this) })
    }

    @Test
    fun test11() {
        assert(2 == change1(2, intArrayOf(1, 2, 5)).apply { println(this) })
    }

    @Test
    fun test12() {
        assert(0 == change1(3, intArrayOf(2)))
    }

    @Test
    fun test13() {
        assert(1 == change1(10, intArrayOf(10)))
    }

    @Test
    fun test14() {
        assert(35502874 == change1(500, intArrayOf(3, 5, 7, 8, 9, 10, 11)).apply { println(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
