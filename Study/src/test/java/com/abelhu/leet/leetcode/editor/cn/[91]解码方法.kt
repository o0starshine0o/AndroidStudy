import org.junit.Assert
import org.junit.Before
import org.junit.Test

//一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
//
// 
//'A' -> "1"
//'B' -> "2"
//...
//'Z' -> "26" 
//
// 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为： 
//
// 
// "AAJF" ，将消息分组为 (1 1 10 6) 
// "KJF" ，将消息分组为 (11 10 6) 
// 
//
// 注意，消息不能分组为 (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。 
//
// 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。 
//
// 题目数据保证答案肯定是一个 32 位 的整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "12"
//输出：2
//解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
// 
//
// 示例 2： 
//
// 
//输入：s = "226"
//输出：3
//解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
// 
//
// 示例 3： 
//
// 
//输入：s = "06"
//输出：0
//解释："06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 100 
// s 只包含数字，并且可能包含前导零。 
// 
//
// Related Topics 字符串 动态规划 👍 1400 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution91 {
    // 这是用递归解决的
    val dp = HashMap<String, Int>()
    private fun numDecodings(s: String): Int {
        // baseCase, 因为题目中说非空, 所以这里可以直接返回1而不是0
        if (s.isBlank()) return 1
        // baseCase, 但凡有前导"0"的都无法解码, 进行剪枝操作
        if (s.startsWith("0")) return 0
        // baseCase
        if (s.length == 1) return 1
        // dp
        if (dp.containsKey(s)) return dp[s] ?: 0

        // 递归遍历, if条件进行剪枝
        val onePrefix = s.substring(0, 1).toInt()
        val left = if (onePrefix in 1..26)  numDecodings(s.substring(1)) else 0
        val twoPrefix = s.substring(0, 2).toInt()
        val right = if (twoPrefix in 1..26) numDecodings(s.substring(2)) else 0

        // 更新dp, 并且返回结果
        return (left + right).apply { dp[s] = this }
    }

    @Before
    fun setup() {
        dp.clear()
    }

    @Test
    fun test0() {
        val s = "01"
        println(s.substring(0, 2))
        println(s.substring(2))
    }

    @Test
    fun test2() {
        val s = "10"
        Assert.assertEquals(1, numDecodings(s))
    }

    @Test
    fun test3() {
        val s = "12"
        Assert.assertEquals(2, numDecodings(s))
    }

    @Test
    fun test4() {
        val s = "226"
        Assert.assertEquals(3, numDecodings(s))
    }

    @Test
    fun test5() {
        val s = "01"
        Assert.assertEquals(0, numDecodings(s))
    }

    @Test
    fun test6() {
        val s = "1111"
        Assert.assertEquals(5, numDecodings(s))
        Assert.assertEquals(3, dp["111"])
    }
}
//leetcode submit region end(Prohibit modification and deletion)
