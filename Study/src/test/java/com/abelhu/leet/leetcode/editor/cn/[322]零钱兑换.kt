import org.junit.Test

//给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回
// -1。 
//
// 你可以认为每种硬币的数量是无限的。 
//
// 
//
// 示例 1： 
//
// 
//输入：coins = [1, 2, 5], amount = 11
//输出：3 
//解释：11 = 5 + 5 + 1 
//
// 示例 2： 
//
// 
//输入：coins = [2], amount = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：coins = [1], amount = 0
//输出：0
// 
//
// 示例 4： 
//
// 
//输入：coins = [1], amount = 1
//输出：1
// 
//
// 示例 5： 
//
// 
//输入：coins = [1], amount = 2
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= coins.length <= 12 
// 1 <= coins[i] <= 231 - 1 
// 0 <= amount <= 104 
// 
// Related Topics 动态规划 
// 👍 1191 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution322 {
    // 自上而下,递归实现
    fun coinChange(coins: IntArray, amount: Int, dp: HashMap<Int, Int> = hashMapOf()): Int {
        // base和exception
        if (amount == 0) return 0
        if (amount < 0) return -1
        // 状态表
        dp[amount]?.apply { return this }
        // 因为要求最小值,所以这里初始化为最大值
        var count = Int.MAX_VALUE
        for (coin in coins) {
            // 状态转移
            when (val result = coinChange(coins, amount - coin, dp)) {
                // 如果没有合适的方案,返回-1
                -1 -> continue
                // 在所有方案中寻找最优解
                else -> count = kotlin.math.min(count, 1 + result)
            }
        }
        return (if (count == Int.MAX_VALUE) -1 else count).apply { dp[amount] = this }
    }

    fun coinChange1(coins: IntArray, amount: Int): Int {
        // exception
        if (amount < 0) return -1
        // 状态保存
        val dp = IntArray(amount + 1) { i -> if (i == 0) 0 else Int.MAX_VALUE }
        for (money in 1..amount) {
            for (coin in coins) {
                when (val compare = money - coin) {
                    in Int.MIN_VALUE until 0 -> continue
                    // 寻找最优解,注意,这里1 + dp[compare]可能会溢出,需要提前-1来抵消
                    else -> dp[money] = kotlin.math.min(dp[money], 1 + dp[compare].let { if (it == Int.MAX_VALUE) it - 1 else it })
                }
            }
        }
        return dp.last().let { if (it == Int.MAX_VALUE) -1 else it }
    }

    @Test
    fun test0a() {
        assert(3 == coinChange(intArrayOf(1, 2, 5), 11))
    }

    @Test
    fun test0b() {
        assert(-1 == coinChange(intArrayOf(2), 3))
    }

    @Test
    fun test0c() {
        assert(0 == coinChange(intArrayOf(1), 0))
    }

    @Test
    fun test0d() {
        assert(1 == coinChange(intArrayOf(1), 1))
    }

    @Test
    fun test0e() {
        assert(2 == coinChange(intArrayOf(1), 2))
    }

    @Test
    fun test0f() {
        assert(20 == coinChange(intArrayOf(1, 2, 5), 100))
    }

    @Test
    fun test0g() {
        assert(20 == coinChange(intArrayOf(186, 419, 83, 408), 6249).apply { print(this) })
    }

    @Test
    fun test1a() {
        assert(3 == coinChange1(intArrayOf(1, 2, 5), 11))
    }

    @Test
    fun test1b() {
        assert(-1 == coinChange1(intArrayOf(2), 3))
    }

    @Test
    fun test1c() {
        assert(0 == coinChange1(intArrayOf(1), 0))
    }

    @Test
    fun test1d() {
        assert(1 == coinChange1(intArrayOf(1), 1))
    }

    @Test
    fun test1e() {
        assert(2 == coinChange1(intArrayOf(1), 2))
    }

    @Test
    fun test1f() {
        assert(20 == coinChange1(intArrayOf(1, 2, 5), 100))
    }

    @Test
    fun test1g() {
        assert(20 == coinChange1(intArrayOf(186, 419, 83, 408), 6249).apply { print(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
