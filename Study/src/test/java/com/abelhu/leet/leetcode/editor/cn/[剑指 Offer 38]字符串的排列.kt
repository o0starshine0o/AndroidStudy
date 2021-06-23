import org.junit.Test

//输入一个字符串，打印出该字符串中字符的所有排列。
//
// 
//
// 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。 
//
// 
//
// 示例: 
//
// 输入：s = "abc"
//输出：["abc","acb","bac","bca","cab","cba"]
// 
//
// 
//
// 限制： 
//
// 1 <= s 的长度 <= 8 
// Related Topics 回溯算法 
// 👍 342 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution38 {
    fun permutation(s: String): Array<String> {
        val result = mutableSetOf<String>()
        dfs("", MutableList(s.length) { i -> s[i] }, result)
        return result.toTypedArray()
    }

    /**
     * 采用深度优先搜索完成回溯算法
     */
    private fun dfs(string: String, unselect: MutableList<Char>, result: MutableSet<String>) {
        // 结束条件
        if (unselect.isEmpty()) result.add(string)
        // unselect为empty走到这里也会直接跳过的,没啥影响
        for (i in unselect.indices) {
            val char = unselect[i]
            dfs(string + char, unselect.apply { removeAt(i) }, result)
            // 需要把删除掉的元素补回来
            unselect.add(i, char)
        }
    }

    @Test
    fun test0() {
        assert(arrayOf("abc", "acb", "bac", "bca", "cab", "cba").contentEquals(permutation("abc").onEach { print("$it,") }))
    }

    @Test
    fun test1() {
        assert(arrayOf("aab", "aba", "baa").contentEquals(permutation("aab").onEach { print("$it,") }))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
