import org.junit.Test

//给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的
// 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。 
//
// candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。 
//
// 对于给定的输入，保证和为 target 的不同组合数少于 150 个。 
//
// 
//
// 示例 1： 
//
// 
//输入：candidates = [2,3,6,7], target = 7
//输出：[[2,2,3],[7]]
//解释：
//2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
//7 也是一个候选， 7 = 7 。
//仅有这两种组合。 
//
// 示例 2： 
//
// 
//输入: candidates = [2,3,5], target = 8
//输出: [[2,2,2,2],[2,3,3],[3,5]] 
//
// 示例 3： 
//
// 
//输入: candidates = [2], target = 1
//输出: []
// 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 2 <= candidates[i] <= 40 
// candidates 的所有元素 互不相同 
// 1 <= target <= 40 
// 
//
// Related Topics 数组 回溯 👍 2521 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution39 {
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        // 先排序, 避免重复结果
        candidates.sort()

        return subCombinationSum(candidates, target, mutableListOf())
    }

    private fun subCombinationSum(candidates: IntArray, target: Int, selected: MutableList<Int>): List<List<Int>> {
        // base case
        // 注意! selected是会变动的, 需要保存它的copy
        if (target == 0) return mutableListOf(selected.toList())
        if (target < 0) return mutableListOf()

        // 保存结果
        val result = mutableListOf<List<Int>>()

        // 遍历
        for (candidate in candidates) {
            // 这是会产生重复结果的地方, 需要剪枝
            if (selected.isNotEmpty() && candidate < selected.last()) continue
            // 做出选择
            selected.add(candidate)
            // DFS
            result.addAll(subCombinationSum(candidates, target - candidate, selected))
            // 撤销选择
            selected.remove(candidate)
        }

        // 返回当前节点数据
        return result
    }

    @Test
    fun test0() {
        println(combinationSum(arrayOf(2, 3, 6, 7).toIntArray(), 7))
    }

    @Test
    fun test1() {
        println(combinationSum(arrayOf(2, 3, 5).toIntArray(), 8))
    }

    @Test
    fun test2() {
        println(combinationSum(arrayOf(2).toIntArray(), 1))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
