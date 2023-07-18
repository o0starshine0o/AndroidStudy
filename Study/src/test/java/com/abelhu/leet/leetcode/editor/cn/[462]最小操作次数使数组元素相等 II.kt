import org.junit.Assert
import org.junit.Test

//给你一个长度为 n 的整数数组 nums ，返回使所有数组元素相等需要的最小操作数。
//
// 在一次操作中，你可以使数组中的一个元素加 1 或者减 1 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：2
//解释：
//只需要两次操作（每次操作指南使一个元素加 1 或减 1）：
//[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,10,2,9]
//输出：16
// 
//
// 
//
// 提示： 
//
// 
// n == nums.length 
// 1 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// 
//
// Related Topics 数组 数学 排序 👍 292 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution462 {
    /**
     * x位于区间[min, max]中, 则使得数组相同的最小操作数为: max - min
     */
    fun minMoves2(nums: IntArray): Int {
        nums.sort()
        val size = nums.size
        var result = 0
        // 只需要遍历一半就够了, 如果size为奇数, 最中间一位可以直接忽略
        // 外层是内层的超集, 所以外层的x一定在内层里存在
        for (i in 0 until size / 2) {
            // x = max - min
            result += (nums[size - i - 1] - nums[i])
        }
        return result
    }

    @Test
    fun test0() {
        Assert.assertEquals(2, minMoves2(intArrayOf(1, 2, 3)))
    }

    @Test
    fun test1() {
        Assert.assertEquals(16, minMoves2(intArrayOf(1, 10, 2, 9)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
