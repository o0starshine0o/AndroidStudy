import org.junit.Test

//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 0 <= n <= 3 * 104 
// 0 <= height[i] <= 105 
// 
// Related Topics 栈 数组 双指针 动态规划 
// 👍 2377 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution42 {
    /**
     * 暴力解法
     * O(N^2)
     */
    fun trap(height: IntArray): Int {
        var result = 0
        for ((index, item) in height.withIndex()) {
            val left = getMax(height, item, 0, index) ?: continue
            val right = getMax(height, item, index + 1, height.size) ?: continue
            result += kotlin.math.min(left, right) - item
        }
        return result
    }

    private fun getMax(height: IntArray, num: Int, start: Int, end: Int): Int? {
        var max = Int.MIN_VALUE
        for (i in start until end) if (height[i] > num) max = kotlin.math.max(max, height[i])
        return if (max == Int.MIN_VALUE) null else max
    }

    fun trap1(height: IntArray): Int {
        // 使用dp来保存height[i]到2边的最大高度
        val leftDp = IntArray(height.size) { 0 }
        val rightDp = IntArray(height.size) { 0 }
        // 使用遍历来保存dp
        if (height.isEmpty()) return 0
        var leftMax = height[0]
        var rightMax = height[height.size - 1]
        // 这里使用一次遍历的技巧,完成2个dp的赋值
        for ((index, item) in height.withIndex()) {
            kotlin.math.max(leftMax, item).apply { leftMax = this;leftDp[index] = this }
            (height.size - 1 - index).also { rightIndex -> kotlin.math.max(rightMax, height[rightIndex]).apply { rightMax = this;rightDp[rightIndex] = this } }
        }
        // 根据trap的思路,使用dp替换暴力搜索
        var result = 0
        for ((index, item) in height.withIndex()) result += kotlin.math.min(leftDp[index], rightDp[index]) - item
        return result
    }

    @Test
    fun test0() {
        assert(6 == trap(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)))
    }

    @Test
    fun test1() {
        assert(9 == trap(intArrayOf(4, 2, 0, 3, 2, 5)))
    }

    @Test
    fun test10() {
        assert(6 == trap1(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)))
    }

    @Test
    fun test11() {
        assert(9 == trap1(intArrayOf(4, 2, 0, 3, 2, 5)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
