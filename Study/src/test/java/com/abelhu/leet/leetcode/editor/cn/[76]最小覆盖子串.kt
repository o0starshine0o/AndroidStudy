import org.junit.Test
import java.util.*

//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
//
// 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
// 
//
// 示例 2： 
//
// 
//输入：s = "a", t = "a"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length, t.length <= 105 
// s 和 t 由英文字母组成 
// 
//
// 
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 1107 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution76 {
    fun minWindow(s: String, t: String): String {
        // 左闭右开区间,保证相同值时没有元素
        var start = 0
        var end = 0
        var left = 0
        var right = 0
        var min = Int.MAX_VALUE
        // 窗口,这里要注意,并非是list,因为t中可能包含重复的字符
        val window = hashMapOf<Char, Int>()
        // t中可能是重复的字符,需要记录所有字符的数量
        val need = HashMap<Char, Int>().apply { t.forEach { char -> set(char, getOrDefault(char, 0) + 1) } }
        var windowSize = 0

        // 右侧增大,扩大窗口
        while (right < s.length) {
            val char = s[right]
            // 向右滑动一位窗口
            right++

            // 如果t中有char,并且window中包含char的数量小于需要的数量,可以认为windowSize又进了一步
            if (t.contains(char) && window.getOrDefault(char, 0) < need.getOrDefault(char, 0)) windowSize++

            // 将此元素添加到窗口中+1
            window[char] = window.getOrDefault(char, 0) + 1

            // 如果window中的有效值数量和t的size相同,意味着当前window已经符合要求,先记录下当前的start和end,然后从左侧收缩窗口
            while (windowSize == t.length) {
                if (right - left < min) {
                    start = left
                    end = right
                    min = right - left
                }

                // 从最左侧开始逐步移除窗口元素
                val char = s[left]
                // 向左滑动一位窗口
                left++

                // 如果t中有char,并且window中未包含char,把window中的有效值数量减1
                if (t.contains(char) && window.getOrDefault(char, 0) == need.getOrDefault(char, 0)) windowSize--

                // 将此元素从窗口中减少1
                window[char] = window.getOrDefault(char, 0) - 1
            }

        }

        // 返回找到的窗口,注意左闭右开开
        return s.substring(start until end)
    }

    @Test
    fun test0() {
        assert("BANC" == minWindow("ADOBECODEBANC", "ABC").apply { println(this) })
    }

    @Test
    fun test1() {
        assert("a" == minWindow("a", "a").apply { println(this) })
    }

    @Test
    fun test2() {
        assert("aa" == minWindow("aa", "aa").apply { println(this) })
    }

    @Test
    fun test3() {
        // "aefgc"
        assert("cwae" == minWindow("cabwefgewcwaefgcf", "cae").apply { println(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
