import org.junit.Test

//给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
//
// 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。 
//
// 说明： 
//
// 
// 字母异位词指字母相同，但排列不同的字符串。 
// 不考虑答案输出的顺序。 
// 
//
// 示例 1: 
//
// 
//输入:
//s: "cbaebabacd" p: "abc"
//
//输出:
//[0, 6]
//
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
// 
//
// 示例 2: 
//
// 
//输入:
//s: "abab" p: "ab"
//
//输出:
//[0, 1, 2]
//
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
// 
// Related Topics 哈希表 
// 👍 513 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution438 {
    fun findAnagrams(s: String, p: String): List<Int> {
        // 先保存下p中的字符串数据
        val map = HashMap<Char, Int>()
        p.forEach { map[it] = (map[it] ?: 0) + 1 }
        // 滑动窗口法
        var left = 0
        var right = 0
        var size = 0
        val result = MutableList(0) { 0 }
        val window = HashMap<Char, Int>()
        // 右侧增加
        while (right < s.length) {
            // 把right对应的char放到窗口中,对应的right自增,size自增
            window[s[right]] = (window[s[right]] ?: 0) + 1
            right++
            size++
            // 如果size和p的长度相等,认为窗口达到了阈值,可以进行判断和移动左侧指针
            while (size >= p.length) {
                if (window.all { map[it.key] == it.value }) result.add(left)
                if (window[s[left]] ?: 0 == 1) window.remove(s[left])
                else window[s[left]] = (window[s[left]] ?: 0) - 1
                left++
                size--
            }
        }
        return result.apply { println(this) }
    }

    @Test
    fun test0() {
        assert(listOf(0, 6).containsAll(findAnagrams("cbaebabacd", "abc")))
    }

    @Test
    fun test1() {
        assert(listOf(0, 1, 2).containsAll(findAnagrams("abab", "ab")))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
