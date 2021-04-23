import org.junit.Test

//你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上
//被小偷闯入，系统会自动报警。 
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。 
//
// 
//
// 示例 1： 
//
// 
//输入：[1,2,3,1]
//输出：4
//解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
//     偷窃到的最高金额 = 1 + 3 = 4 。 
//
// 示例 2： 
//
// 
//输入：[2,7,9,3,1]
//输出：12
//解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
//     偷窃到的最高金额 = 2 + 9 + 1 = 12 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 400 
// 
// Related Topics 动态规划 
// 👍 1418 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution198 {
    fun rob(nums: IntArray): Int {
        if (nums.size == 1) return nums.first()
        // 动归问题,先设定一个状态转移方程
        // 最多选择空出2个位置,不能在连续空出3个位置了
        // f(x) = max(f(x-1), num(x) + f(x-2), num(x) + f(x-3))
        // base: f(0) = nums[0];f(1) = num[1]
        // 自底而上,需要一个dp来记录,这里只需要一维的dp就可以了
        val dp = IntArray(nums.size) { nums[it] }
        dp[1] = kotlin.math.max(dp[0], dp[1])
        for (i in 2 until nums.size) dp[i] = kotlin.math.max(dp[i - 1], nums[i] + dp[i - 2])
        return dp.last()
    }

    @Test
    fun test0() {
        assert(4 == rob(intArrayOf(1, 2, 3, 1)))
    }

    @Test
    fun test1() {
        assert(12 == rob(intArrayOf(2, 7, 9, 3, 1)))
    }

    @Test
    fun test2() {
        // dp: [2,1,3,?]
        assert(4 == rob(intArrayOf(2, 1, 1, 2)))
    }

    @Test
    fun test3() {
        // dp: [2,1,3,3,?]
        assert(6 == rob(intArrayOf(2, 1, 1, 1, 3)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
