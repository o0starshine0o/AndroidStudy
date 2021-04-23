import org.junit.Test

//你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的
//房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。 
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,3,2]
//输出：3
//解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,1]
//输出：4
//解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
//     偷窃到的最高金额 = 1 + 3 = 4 。 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 1000 
// 
// Related Topics 动态规划 
// 👍 662 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution213 {
    fun rob(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums.first()
        // 相比198题,有3种情况
        // 1, 头尾都不抢
        // 2, 头不抢
        // 3, 尾不抢
        // 所以算法是去掉头尾,只计算中间的部分,最后再加上头或者尾就可以了
        return kotlin.math.max(rob2(nums.sliceArray(0..nums.size - 2)), rob2(nums.sliceArray(1 until nums.size)))
    }

    private fun rob2(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
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
        assert(3 == rob(intArrayOf(2, 3, 2)))
    }

    @Test
    fun test1() {
        assert(4 == rob(intArrayOf(1, 2, 3, 1)))
    }

    @Test
    fun test3() {
        assert(0 == rob(intArrayOf(0)))
    }

    @Test
    fun test4() {
        assert(3 == rob(intArrayOf(2, 3)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
