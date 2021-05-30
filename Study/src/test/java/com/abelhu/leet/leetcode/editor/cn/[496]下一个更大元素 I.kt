import org.junit.Test
import java.util.*
import kotlin.collections.HashMap

//给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
//
// 请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。 
//
// nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
//输出: [-1,3,-1]
//解释:
//    对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
//    对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
//    对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。 
//
// 示例 2: 
//
// 
//输入: nums1 = [2,4], nums2 = [1,2,3,4].
//输出: [3,-1]
//解释:
//    对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
//    对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums1.length <= nums2.length <= 1000 
// 0 <= nums1[i], nums2[i] <= 104 
// nums1和nums2中所有整数 互不相同 
// nums1 中的所有整数同样出现在 nums2 中 
// 
//
// 
//
// 进阶：你可以设计一个时间复杂度为 O(nums1.length + nums2.length) 的解决方案吗？ 
// Related Topics 栈 
// 👍 428 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution496 {
    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
        // 把num2的每一个元素都计算出来,放到map中,方便后面用O(1)来查找
        val map = HashMap<Int, Int>()
        // 遍历num1,去map中查找结果
        val result = IntArray(nums1.size) { 0 }
        // 使用栈结构
        val stack = Stack<Int>()
        // 这里是使用后向遍历的方式
        for (i in nums2.indices.reversed()) {
            /**
             * 这一步操作是关键
             * 需要把之前入栈的小于当前元素的值的数据都出栈,直到找到大于等于当前元素的元素
             * ** 注意,stack不能用first,因为这样会返回数组的第一个元素,要用peek拿取堆栈的头部位置元素
             */
            while (stack.isNotEmpty() && stack.peek() <= nums2[i]) stack.pop()
            // 保存结果
            map[nums2[i]] = if (stack.size > 0) stack.peek() else -1
            // 这里需要把当前元素保存到堆栈中,方便下一次查询
            stack.push(nums2[i])
        }
        // 这里只是做一步简单的适配
        for ((index, num) in nums1.withIndex()) result[index] = map[num] ?: -1
        // 返回最终结果
        return result
    }

    @Test
    fun test0() {
        assert(intArrayOf(-1, 3, -1).contentEquals(nextGreaterElement(intArrayOf(4, 1, 2), intArrayOf(1, 3, 4, 2))))
    }

    @Test
    fun test1() {
        assert(intArrayOf(3, -1).contentEquals(nextGreaterElement(intArrayOf(2, 4), intArrayOf(1, 2, 3, 4))))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
