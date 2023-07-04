//import com.abelhu.leet.utils.log
import org.junit.Test

//给定一正整数数组 nums，nums 中的相邻整数将进行浮点除法。例如， [2,3,4] -> 2 / 3 / 4 。
//
// 
// 例如，nums = [2,3,4]，我们将求表达式的值 "2/3/4"。 
// 
//
// 但是，你可以在任意位置添加任意数目的括号，来改变算数的优先级。你需要找出怎么添加括号，以便计算后的表达式的值为最大值。 
//
// 以字符串格式返回具有最大值的对应表达式。 
//
// 注意：你的表达式不应该包含多余的括号。 
//
// 
//
// 示例 1： 
//
// 
//输入: [1000,100,10,2]
//输出: "1000/(100/10/2)"
//解释: 1000/(100/10/2) = 1000/((100/10)/2) = 200
//但是，以下加粗的括号 "1000/((100/10)/2)" 是冗余的，
//因为他们并不影响操作的优先级，所以你需要返回 "1000/(100/10/2)"。
//
//其他用例:
//1000/(100/10)/2 = 50
//1000/(100/(10/2)) = 50
//1000/100/10/2 = 0.5
//1000/100/(10/2) = 2
// 
//
// 
//
// 示例 2: 
//
// 
//输入: nums = [2,3,4]
//输出: "2/(3/4)"
//解释: (2/(3/4)) = 8/3 = 2.667
//可以看出，在尝试了所有的可能性之后，我们无法得到一个结果大于 2.667 的表达式。
// 
//
// 
//
// 说明: 
//
// 
// 1 <= nums.length <= 10 
// 2 <= nums[i] <= 1000 
// 对于给定的输入只有一种最优除法。 
// 
//
// Related Topics 数组 数学 动态规划 👍 209 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution553 {
    private fun optimalDivision(nums: IntArray): String {
        //
        val length = nums.size
        // 构建一个 length * length 的数组dp(一个二维矩阵)
        // 如果i<j(矩阵上半部分), dp[i][j]表示从i到j能计算得到的最大值
        // 如果i>j(矩阵下半部分), dp[i][j]表示从i到j能计算得到的最小值
        // 如果i==j(对角线), dp[i][j]因为只有一个数, 代表了nums中对应的数字
        // 先对dp进行初始化, 对角线为nums中对应的数字, 其他部分为0
        // 因为需要输出计算表达式, 所以在dp中缓存一下取得这个数字的表达式, 用key-value的形式
        val dp = Array(length) { Array(length) { .0f to "" } }
        // 按照对角线顺序遍历数组
//        println("对角遍历")
        var x = 0
        var y = 0
        var i = 0
        var j = 0
        while (x < length && y < length) {
            dp[i][j] = when {
                // 对角线, 只有一个元素, 最大值和最小值相同, 都为nums对应的数字
                i == j -> nums[i].toFloat() to nums[i].toString()
                // 元素在矩阵下半部分, 计算最小值
                i > j -> getMin(dp, i, j)
                // 元素在矩阵上半部分, 计算最大值
                else -> getMax(dp, i, j)
            }
//            }.log { "dp[$i][$j] = (${it.first}, ${it.second})" }

            // 双指针往右下方运动
            i++
            j++

            // 到达边界, 需要折返
            if (j >= length) {
                y++
                i = y
                j = 0
            } else if (i >= length) {
                x++
                i = 0
                j = x
            }
        }

//        // 这就是最终最大值
//        println("max: ${dp[0][length - 1].first}, ${dp[0][length - 1].second}")
//        println("min: ${dp[length -1][0].first}, ${dp[length -1][0].second}")

        return dp[0][length - 1].second
    }

    private fun getMax(dp: Array<Array<Pair<Float, String>>>, i: Int, j: Int): Pair<Float, String> {
        var max = Int.MIN_VALUE.toFloat()
        var expression = ""
        for (row in i until j) {
            val result = dp[i][row].first / dp[j][row + 1].first
            if (result > max) {
                max = result
                var divisor = dp[j][row + 1].second
                divisor = if (j == row +1) divisor else "($divisor)"
                expression = "${dp[i][row].second}/$divisor"
            }
        }
        return max to expression
    }

    private fun getMin(dp: Array<Array<Pair<Float, String>>>, i: Int, j: Int): Pair<Float, String> {
        var min = Int.MAX_VALUE.toFloat()
        var expression = ""
        for (col in j until i) {
            val result = dp[col][j].first / dp[col + 1][i].first
            if (result < min){
                min = result
                var divisor = dp[col + 1][i].second
                divisor = if (i == col +1) divisor else "($divisor)"
                expression = "${dp[col][j].second}/$divisor"
            }
        }
        return min to expression
    }

    /**
     * 对角线序遍历数组
     */
    @Test
    fun test0() {
        val size = 4
        var num = 0
        val dp = Array(size) { Array(size) { num++ } }
        // 行序遍历
        println("行序遍历")
        for (i in 0 until size) {
            for (j in 0 until size) {
                print("${dp[i][j]},")
            }
            println()
        }

        // 对角遍历
        println("对角遍历")
        var x = 0
        var y = 0
        var i = 0
        var j = 0
        while (x < size && y < size) {
            print("${dp[i][j]},")
            i++
            j++

            if (j >= size) {
                y++
                i = y
                j = 0
                println()
            } else if (i >= size) {
                x++
                i = 0
                j = x
                println()
            }
        }
    }

    @Test
    fun test1() {
        optimalDivision(listOf(100, 10, 2).toIntArray())
    }

    @Test
    fun test2() {
        optimalDivision(listOf(1000, 100, 10, 2).toIntArray())
    }

    @Test
    fun test3() {
        optimalDivision(listOf(2, 3, 4).toIntArray())
    }
}
//leetcode submit region end(Prohibit modification and deletion)
