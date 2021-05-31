import org.junit.Test
import java.util.*

//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回滑动窗口中的最大值。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,-1], k = 1
//输出：[1,-1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [9,11], k = 2
//输出：[11]
// 
//
// 示例 5： 
//
// 
//输入：nums = [4,-2], k = 2
//输出：[4] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// -104 <= nums[i] <= 104 
// 1 <= k <= nums.length 
// 
// Related Topics 堆 Sliding Window 
// 👍 1003 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution239 {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        val result = IntArray(nums.size - k + 1) { 0 }
        // 构造大顶堆
        val queue = PriorityQueue<Int>() { a, b -> b - a }
        for (i in nums.indices) {
            if (i >= k) queue.remove(nums[i - k])
            queue.offer(nums[i])
            if (i - k + 1 >= 0) result[i - k + 1] = queue.peek() ?: -1
        }
        return result
    }

    fun maxSlidingWindow1(nums: IntArray, k: Int): IntArray {
        val result = IntArray(nums.size - k + 1) { 0 }
        // 构造大顶堆
        val queue = PriorityStack()
        for (i in nums.indices) {
            if (i >= k) queue.remove(nums[i - k])
            queue.offer(nums[i])
            if (i - k + 1 >= 0) result[i - k + 1] = queue.peek()
        }
        return result
    }

    /**
     * 构建一个单调递减的堆
     */
    class PriorityStack {
        private val queue = LinkedList<Int>()
        fun remove(n: Int) = Unit.apply { if (n >= queue.first) queue.removeFirst() }

        fun offer(n: Int) {
            // 比当前要插入的数据小就直接出栈
            while (queue.isNotEmpty() && queue.last < n) queue.removeLast()
            // 这时候再放进去n,可以保证在n之前的都比n大
            queue.add(n)
        }

        fun peek(): Int = queue.first
    }

    @Test
    fun test0() {
        assert(intArrayOf(3, 3, 5, 5, 6, 7).contentEquals(maxSlidingWindow(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3)))
    }

    @Test
    fun test1() {
        assert(intArrayOf(1).contentEquals(maxSlidingWindow(intArrayOf(1), 1)))
    }

    @Test
    fun test2() {
        assert(intArrayOf(1, -1).contentEquals(maxSlidingWindow(intArrayOf(1, -1), 1)))
    }

    @Test
    fun test3() {
        assert(intArrayOf(11).contentEquals(maxSlidingWindow(intArrayOf(9, 11), 2)))
    }

    @Test
    fun test4() {
        assert(intArrayOf(4).contentEquals(maxSlidingWindow(intArrayOf(4, -2), 2)))
    }

    @Test
    fun test10() {
        assert(intArrayOf(3, 3, 5, 5, 6, 7).contentEquals(maxSlidingWindow1(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3)))
    }

    @Test
    fun test11() {
        assert(intArrayOf(1).contentEquals(maxSlidingWindow1(intArrayOf(1), 1)))
    }

    @Test
    fun test12() {
        assert(intArrayOf(1, -1).contentEquals(maxSlidingWindow1(intArrayOf(1, -1), 1)))
    }

    @Test
    fun test13() {
        assert(intArrayOf(11).contentEquals(maxSlidingWindow1(intArrayOf(9, 11), 2)))
    }

    @Test
    fun test14() {
        assert(intArrayOf(4).contentEquals(maxSlidingWindow1(intArrayOf(4, -2), 2)))
    }

    @Test
    fun test15() {
        assert(intArrayOf(7, 7, 7, 7, 7).contentEquals(maxSlidingWindow1(intArrayOf(-7, -8, 7, 5, 7, 1, 6, 0), 4)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
