import org.junit.Test

//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。 
//
// 
//
// 示例 1： 
//
// 
//输入：strs = ["flower","flow","flight"]
//输出："fl"
// 
//
// 示例 2： 
//
// 
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。 
//
// 
//
// 提示： 
//
// 
// 1 <= strs.length <= 200 
// 0 <= strs[i].length <= 200 
// strs[i] 仅由小写英文字母组成 
// 
//
// Related Topics 字典树 字符串 👍 3146 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution14 {
    /**
     * 对链表的每一层进行遍历
     *
     * 更好的方案: 二分查找
     */
    fun longestCommonPrefix(strings: Array<String>): String {
        // 先确保一定不为空
        if (strings.isEmpty()) return ""
        val result = mutableListOf<Char>()
        // 当前遍历的层数
        var layerIndex = 0
        while (true) {
            val layerChar = traverseLayer(strings, layerIndex) ?: break
            result.add(layerChar)
            layerIndex++
        }
        return result.joinToString("")
    }

    private fun traverseLayer(strings: Array<String>, layer: Int): Char? {
        // 这里还存在一种case
        if (strings[0].length <= layer) return null
        // 当前层数对应的char
        val char = strings[0][layer]
        for (str in strings) {
            if (str.length <= layer) return null
            if (str[layer] != char) return null
        }
        return char
    }

    @Test
    fun test0() {
        val solution = Solution14()
        val result = solution.longestCommonPrefix(arrayOf("flower","flow","flight")).apply {
            print(this)
        }
        assert("fl" == result)
    }

    @Test
    fun test1() {
        val solution = Solution14()
        val result = solution.longestCommonPrefix(arrayOf("dog","racecar","car")).apply {
            print(this)
        }
        assert("" == result)
    }

    @Test
    fun test2() {
        val solution = Solution14()
        val result = solution.longestCommonPrefix(arrayOf()).apply {
            print(this)
        }
        assert("" == result)
    }

    @Test
    fun test3() {
        val solution = Solution14()
        val result = solution.longestCommonPrefix(arrayOf("")).apply {
            print(this)
        }
        assert("" == result)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
