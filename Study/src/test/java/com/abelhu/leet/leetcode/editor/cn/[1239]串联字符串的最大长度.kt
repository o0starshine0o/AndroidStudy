import org.junit.Test

//给定一个字符串数组 arr，字符串 s 是将 arr 某一子序列字符串连接所得的字符串，如果 s 中的每一个字符都只出现过一次，那么它就是一个可行解。
//
// 请返回所有可行解 s 中最长长度。 
//
// 
//
// 示例 1： 
//
// 输入：arr = ["un","iq","ue"]
//输出：4
//解释：所有可能的串联组合是 "","un","iq","ue","uniq" 和 "ique"，最大长度为 4。
// 
//
// 示例 2： 
//
// 输入：arr = ["cha","r","act","ers"]
//输出：6
//解释：可能的解答有 "chaers" 和 "acters"。
// 
//
// 示例 3： 
//
// 输入：arr = ["abcdefghijklmnopqrstuvwxyz"]
//输出：26
// 
//
// 
//
// 提示： 
//
// 
// 1 <= arr.length <= 16 
// 1 <= arr[i].length <= 26 
// arr[i] 中只含有小写英文字母 
// 
// Related Topics 位运算 回溯算法 
// 👍 133 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1239 {
    fun maxLength(arr: List<String>): Int {
        // 先把String转换为int
        val array = arr.map { toInt(it) }.filter { it != Int.MIN_VALUE }
        // dp保存当前计算结果[当前值] = 长度
        val dp = HashMap<Int, Int>()
        // 从根节点进入回溯算法
        back(array, dp, 0)
        // 返回最开始的值,因为最终答案一定藏在dp[0],也就是根节点中
        return dp[0] ?: 0
    }

    /**
     * 采用回溯算法
     */
    private fun back(array: List<Int>, dp: HashMap<Int, Int>, current: Int): Int {
        // 如果dp有保存,直接返回
        return dp[current] ?: Unit.let {
            // 先判断当前节点1的个数,如果子节点有更大的值,在dp表中更新
            var max = getSize(current)
            // 如果current和int的与值为0,表示2者没有相同的字符
            for (int in array) if (int and current == 0) max = kotlin.math.max(max, back(array, dp, int or current))
            // 更新最大值
            max
            // 进行dp保存
        }.apply { dp[current] = this }

    }

    /**
     * 把String映射到int上去
     */
    private fun toInt(string: String): Int {
        var result = 0
        for (c in string) {
            val shift = c - 'a'
            val bit = 1 shl shift
            // 只保留当前位,之后进行异或,如果为0表示当前位为1,也就是说有重复的字符
            if (result and bit xor bit == 0) return Int.MIN_VALUE
            result = result or (1 shl shift)
        }
        return result
    }

    /**
     * 获取int中有多少位是1
     */
    private fun getSize(int: Int): Int {
        var result = 0
        for (i in 0..31) {
            if (int shr i and 1 == 1) result++
        }
        return result
    }

    /**
     * 先验证小方法的正确性
     */
    @Test
    fun testGetSize(){
        assert(1 == getSize(1))
        assert(2 == getSize(3))
        assert(3 == getSize(7))
        assert(4 == getSize(15))
        assert(5 == getSize(31))
        assert(6 == getSize(63))
        assert(7 == getSize(127))
    }


    /**
     * 先验证小方法的正确性
     */
    @Test
    fun testToInt(){
        assert(1 shl 0 == toInt("a").apply { println(this) })
        assert(1 shl 1 == toInt("b").apply { println(this) })
        assert(1 shl 2 == toInt("c").apply { println(this) })
        assert(1 shl 3 == toInt("d").apply { println(this) })
        assert(1 shl 4 == toInt("e").apply { println(this) })
        assert(1 shl 5 == toInt("f").apply { println(this) })
        assert(1 shl 6 == toInt("g").apply { println(this) })
    }

    @Test
    fun test0(){
        assert(4 == maxLength(listOf("un","iq","ue")).apply { println(this) })
    }

    @Test
    fun test1(){
        assert(6 == maxLength(listOf("cha","r","act","ers")).apply { println(this) })
    }

    @Test
    fun test2(){
        assert(26 == maxLength(listOf("abcdefghijklmnopqrstuvwxyz")).apply { println(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
