import org.junit.Test

//斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
//
// 
//F(0) = 0，F(1) = 1
//F(n) = F(n - 1) + F(n - 2)，其中 n > 1
// 
//
// 给你 n ，请计算 F(n) 。 
//
// 
//
// 示例 1： 
//
// 
//输入：2
//输出：1
//解释：F(2) = F(1) + F(0) = 1 + 0 = 1
// 
//
// 示例 2： 
//
// 
//输入：3
//输出：2
//解释：F(3) = F(2) + F(1) = 1 + 1 = 2
// 
//
// 示例 3： 
//
// 
//输入：4
//输出：3
//解释：F(4) = F(3) + F(2) = 2 + 1 = 3
// 
//
// 
//
// 提示： 
//
// 
// 0 <= n <= 30 
// 
// Related Topics 数组 
// 👍 262 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution509 {
    // 根据定义来的,最原始的递归解法
    fun fib0(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fib0(n - 1) + fib0(n - 2)
        }
    }

    // 使用dp来缓存计算过的数据
    fun fib1(n: Int, dp: IntArray = IntArray(n + 1) { 0 }): Int {
        if (dp[n] != 0) return dp[n]
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fib1(n - 1) + fib1(n - 2)
        }.apply { dp[n] = this }
    }

    // 采用状态转移方程,动态规划
    fun fib2(n: Int, dp: IntArray = IntArray(n) { 0 }): Int {
        var i = 0
        while (i < n) {
            dp[i] = when (i) {
                0 -> 1
                1 -> 1
                // 这里就是状态转移方程了
                else -> dp[i - 1] + dp[i - 2]
            }
            i++
        }
        return dp.last()
    }

    // 采用状态转移方程,动态规划,优化存储空间
    fun fib3(n: Int): Int {
        var i = 0
        var left = 0
        var right = 0
        while (i < n) {
            left = when (i) {
                0 -> 1
                1 -> 1
                // 这里就是状态转移方程了
                else -> left + right
            }
            // 交换left和right
            left = left.xor(right)
            right = left.xor(right)
            left = left.xor(right)
            i++
        }
        return right
    }

    @Test
    fun test0() {
        assert(1 == fib0(2))
        assert(2 == fib0(3))
        assert(3 == fib0(4))
        assert(6765 == fib0(20))
    }

    @Test
    fun test1() {
        assert(1 == fib1(2))
        assert(2 == fib1(3))
        assert(3 == fib1(4))
        assert(6765 == fib1(20))
    }

    @Test
    fun test2() {
        assert(1 == fib2(2))
        assert(2 == fib2(3))
        assert(3 == fib2(4))
        assert(6765 == fib2(20))
    }

    @Test
    fun test3() {
        assert(1 == fib3(2))
        assert(2 == fib3(3))
        assert(3 == fib3(4))
        assert(6765 == fib3(20))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
