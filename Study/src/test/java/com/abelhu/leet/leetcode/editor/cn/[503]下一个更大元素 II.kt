import org.junit.Test
import java.util.*

//给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第
//一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。 
//
// 示例 1: 
//
// 
//输入: [1,2,1]
//输出: [2,-1,2]
//解释: 第一个 1 的下一个更大的数是 2；
//数字 2 找不到下一个更大的数； 
//第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
// 
//
// 注意: 输入数组的长度不会超过 10000。 
// Related Topics 栈 
// 👍 433 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution503 {
    fun nextGreaterElements(nums: IntArray): IntArray {
        // 把数组长度翻倍,模拟循环数组
        val result = IntArray(nums.size shl 1) { 0 }
        val stack = Stack<Int>()
        for (i in (result.indices).reversed()) {
            val num = nums[i % nums.size]
            while (stack.isNotEmpty() && stack.peek() <= num) stack.pop()
            result[i] = if (stack.isEmpty()) -1 else stack.peek()
            stack.push(num)
        }
        return result.sliceArray(nums.indices)
    }

    @Test
    fun test0() {
        assert(intArrayOf(2, -1, 2).contentEquals(nextGreaterElements(intArrayOf(1, 2, 1))))
    }

    @Test
    fun test1() {
        assert(intArrayOf(4, 2, 4, -1, 4).contentEquals(nextGreaterElements(intArrayOf(2, 1, 2, 4, 3))))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
