import org.junit.Assert
import org.junit.Test

//给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
//
// 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "bbbab"
//输出：4
//解释：一个可能的最长回文子序列为 "bbbb" 。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出：2
//解释：一个可能的最长回文子序列为 "bb" 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由小写英文字母组成 
// 
//
// Related Topics 字符串 动态规划 👍 1059 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution516 {
    private fun longestPalindromeSubseq(s: String): Int {
        val length = s.length
        // 注意, dp保存的是[i, j]的最长回文子序列
        val dp = Array(length) { Array(length) { 0 } }

        // 注意, 这里的遍历顺序, i后往前, j从i往后
        // 这样所有的子问题都会被先计算出来
        for (i in length - 1 downTo 0) {
            for (j in i until length) {
                dp[i][j] = when {
                    // 初始化条件
                    i == j -> 1
                    // 剩下的分2种不同情况
                    // abba的长度为2 + bb
                    s[i] == s[j] -> dp[i + 1][j - 1] + 2
                    // abbc的长度为max(abb, bbc)
                    else -> kotlin.math.max(dp[i + 1][j], dp[i][j - 1])
                }
            }
        }

        return dp[0][length - 1]
    }

    @Test
    fun test0() {
        val s = "bbbb"
        Assert.assertEquals(4, longestPalindromeSubseq(s))
    }

    @Test
    fun test1() {
        val s = "cbbd"
        Assert.assertEquals(2, longestPalindromeSubseq(s))
    }

    @Test
    fun test2() {
        val s = "abab"
        Assert.assertEquals(3, longestPalindromeSubseq(s))
    }

    @Test
    fun test3() {
        val s = "ababa"
        Assert.assertEquals(5, longestPalindromeSubseq(s))
    }

    @Test
    fun test5() {
        val s = "a"
        Assert.assertEquals(1, longestPalindromeSubseq(s))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
