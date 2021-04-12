import org.junit.Test

//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
//
// 示例: 
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//] 
// Related Topics 回溯算法 
// 👍 1285 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution46 {
    fun permute(nums: IntArray, include: MutableList<Int> = mutableListOf(), result: MutableList<List<Int>> = mutableListOf()): List<List<Int>> {
        if (include.size == nums.size) return result.apply { add(include) }
        for (num in nums.filter { !include.contains(it) }) {
            include.add(num)
            permute(nums, include.toMutableList(), result)
            include.remove(num)
        }
        return result
    }

    @Test
    fun test1() {
        permute(intArrayOf(1, 2, 3)).forEach { print(it) }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
