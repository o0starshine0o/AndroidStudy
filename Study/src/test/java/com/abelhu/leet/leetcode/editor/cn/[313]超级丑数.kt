import org.junit.Assert
import org.junit.Test
import java.util.PriorityQueue

//超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
//
// 给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。 
//
// 题目数据保证第 n 个 超级丑数 在 32-bit 带符号整数范围内。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 12, primes = [2,7,13,19]
//输出：32 
//解释：给定长度为 4 的质数数组 primes = [2,7,13,19]，前 12 个超级丑数序列为：[1,2,4,7,8,13,14,16,19,26,
//28,32] 。 
//
// 示例 2： 
//
// 
//输入：n = 1, primes = [2,3,5]
//输出：1
//解释：1 不含质因数，因此它的所有质因数都在质数数组 primes = [2,3,5] 中。
// 
//
// 
//
// 
// 
// 
// 提示： 
// 
// 
// 
//
// 
// 1 <= n <= 10⁵ 
// 1 <= primes.length <= 100 
// 2 <= primes[i] <= 1000 
// 题目数据 保证 primes[i] 是一个质数 
// primes 中的所有值都 互不相同 ，且按 递增顺序 排列 
// 
//
// Related Topics 数组 数学 动态规划 👍 380 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution313 {
    private fun nthSuperUglyNumber(n: Int, primes: IntArray): Int {
        // 使用一个优先队列, 保存arrayPointIndexes中每个primes对应位置的最小值和具体的位置
        // 注意! 如何构造优先队列
        val queue = PriorityQueue<Int> { a, b -> a - b }
        // 把arrayPointIndexes都入列
        primes.forEach { queue.offer(it) }
        // 注意! 特殊条件
        if (n < 2) return 1
        // 注意! 起始条件
        for (i in 2 until n) {
            // 取得最小值
//            val minValue = queue.poll().log { value -> "minValue: $value" } ?: return 1
            val minValue = queue.poll() ?: return 1
            // 所有primes乘以最小值, 并且入队
            for (prime in primes) {
                // 注意! 要以newValue去判断
                // 注意! 不要超过限制
                if (prime <= Int.MAX_VALUE / minValue) queue.offer(prime * minValue)
                // 注意!!! 这一步是精髓, 因为之后的所有数字之后都会出现, 这里就不用再计算了
                if (minValue % prime == 0) break
            }
        }

        return queue.poll() ?: 1
    }

    @Test
    fun test0() {
        Assert.assertEquals(32, nthSuperUglyNumber(12, listOf(2, 7, 13, 19).toIntArray()))
    }

    @Test
    fun test1() {
        Assert.assertEquals(1, nthSuperUglyNumber(1, listOf(2, 3, 5).toIntArray()))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
