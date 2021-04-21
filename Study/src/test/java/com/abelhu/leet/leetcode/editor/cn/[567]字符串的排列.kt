import org.junit.Test

//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
//
// 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。 
//
// 
//
// 示例 1： 
//
// 
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
// 
//
// 示例 2： 
//
// 
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
// 
//
// 
//
// 提示： 
//
// 
// 输入的字符串只包含小写字母 
// 两个字符串的长度都在 [1, 10,000] 之间 
// 
// Related Topics 双指针 Sliding Window 
// 👍 339 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution567 {
    fun checkInclusion(s1: String, s2: String): Boolean {
        // 先对s1进行处理:因为s1中可能包含重复的字符
        val map = HashMap<Char, Int>()
        s1.forEach { char -> map[char] = (map[char] ?: 0) + 1 }

        // 定义下滑动窗口
        var right = 0
        var left = 0
        val window = HashMap<Char, Int>()

        // 滑动窗口右侧
        while (right < s2.length) {
            val char = s2[right]
            right++
            // 增大窗口时,window的char就对应的+1
            window[char] = (window[char] ?: 0) + 1

            // 因为window中不能有其他元素,也就是说如果window的长度大于s1了,就需要收缩
            while (right - left >= s1.length) {
                // 如果map为空,认为目前窗口不再包含其他s1中未出现的char
                if (window.all { map[it.key] == it.value }) return true

                val leftChar = s2[left]
                left++
                // 减少窗口时,window的char就对应的-1
                if (window[leftChar] == 1) window.remove(leftChar)
                else window[leftChar] = (window[leftChar] ?: 0) - 1
            }
        }
        return false
    }

    @Test
    fun test0() {
        assert(checkInclusion("ab", "eidbaooo"))
    }

    @Test
    fun test1() {
        assert(!checkInclusion("ab", "eidboaoo"))
    }

    @Test
    fun test2() {
        assert(checkInclusion("a", "ab"))
    }

    @Test
    fun test3() {
        assert(checkInclusion("adc", "dcda"))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
