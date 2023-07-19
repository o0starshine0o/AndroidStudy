import org.junit.Assert
import org.junit.Test

//正整数 n 代表生成括号的对数，请设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
//
// 
//
// 
// 注意：本题与主站 22 题相同： https://leetcode-cn.com/problems/generate-parentheses/ 
//
// Related Topics 字符串 动态规划 回溯 👍 74 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution085 {
    fun generateParenthesis(n: Int): List<String> {
        val parenthesis = mutableListOf<String>()
        generateParenthesis(parenthesis, StringBuilder(), n, n)
        return parenthesis
    }

    /**
     * @param combinations 保存着所有符合条件的组合
     * @param builder 当前字符串
     * @param left 左侧还剩几个
     * @param right 右侧还剩几个
     */
    private fun generateParenthesis(parenthesis: MutableList<String>, builder: StringBuilder, left: Int, right: Int) {
        // 结束条件, 到达第2n层, left和right都被消耗空
        if (left <= 0 && right <= 0) {
            parenthesis.add(builder.toString())
//            parenthesis.add(builder.toString().log { "$it\n" })
        }

        // 进入左子树
        if (left > 0) {
            generateParenthesis(parenthesis, builder.append('('), left - 1, right)
            // 撤销选择
            builder.deleteCharAt(builder.length - 1)
        }
        // 进入右子树, 必须left<right, 否则就不是有效的组合了")))"
        if (right > 0 && left < right) {
            generateParenthesis(parenthesis, builder.append(')'), left, right - 1)
            // 撤销选择
            builder.deleteCharAt(builder.length - 1)
        }
    }

    @Test
    fun test0() {
        Assert.assertEquals(5, generateParenthesis(3).size)
    }

    @Test
    fun test1() {
        Assert.assertEquals(1, generateParenthesis(1).size)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
