import org.junit.Test
import kotlin.math.pow

//二进制手表顶部有 4 个 LED 代表 小时（0-11），底部的 6 个 LED 代表 分钟（0-59）。每个 LED 代表一个 0 或 1，最低位在右侧。
// 
//
// 
// 例如，下面的二进制手表读取 "3:25" 。 
// 
//
// 
//
// （图源：WikiMedia - Binary clock samui moon.jpg ，许可协议：Attribution-ShareAlike 3.0 
//Unported (CC BY-SA 3.0) ） 
//
// 给你一个整数 turnedOn ，表示当前亮着的 LED 的数量，返回二进制手表可以表示的所有可能时间。你可以 按任意顺序 返回答案。 
//
// 小时不会以零开头： 
//
// 
// 例如，"01:00" 是无效的时间，正确的写法应该是 "1:00" 。 
// 
//
// 分钟必须由两位数组成，可能会以零开头： 
//
// 
// 例如，"10:2" 是无效的时间，正确的写法应该是 "10:02" 。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：turnedOn = 1
//输出：["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
// 
//
// 示例 2： 
//
// 
//输入：turnedOn = 9
//输出：[]
// 
//
// 
//
// 解释： 
//
// 
// 0 <= turnedOn <= 10 
// 
// Related Topics 位运算 回溯算法 
// 👍 274 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution401 {
    fun readBinaryWatch(turnedOn: Int): List<String> {
        val result = mutableSetOf<String>()
        turnOn(0, turnedOn, 0, result)
        return result.toList()
    }

    /**
     * @param num 当前的数字,使用一个int的低10位来代表10个指示灯
     * @param left 还剩下多少灯可以被点亮
     * @param start 进行剪枝,表示从第几位开始点灯,也就意味着这是第几层
     */
    fun turnOn(num: Int, left: Int, start: Int = 0, set: MutableSet<String>): String? {
        // 已经没有可以被点亮的灯了,直接保存结果
        if (left == 0) return save(num)?.apply { set.add(this) }
        // 点亮第i位
        turnOn(turnOn(num, start), left - 1, start + 1, set)
        // 不点亮第i位,这里进行剪枝,如果剩余的层数不够了,那么剩余的灯必须全部点亮,不点亮第i位的这个情况这个就不用再计算了
        if (9 - start >= left) turnOn(num, left, start + 1, set)
        return null
    }

    /**
     * 把num的第i位点亮,i从0开始
     * 次序决定了num的第i位在运算前不可能为1
     */
    private fun turnOn(num: Int, i: Int) = num or (1 shl i)

    private fun save(num: Int): String? {
        val hour = binaryToDecimal(num shr 6, 4)
        val minute = binaryToDecimal(num and 0b111111, 6)
        return if (hour < 12 && minute < 60) "$hour:${String.format("%02d", minute)}" else null
    }

    /**
     * 2进制转10进制
     */
    private fun binaryToDecimal(num: Int, end: Int): Int {
        var result = 0
        // 先移i位,获取到最后一位的值,进行冥运算,再累加
        for (i in 0 until end) result += (((num shr i) and 1) * 2.0.pow(i)).toInt()
        return result
    }

    @Test
    fun testBinaryToDecimal() {
        assert(3 == binaryToDecimal(0b11, 10))
        assert(7 == binaryToDecimal(0b111, 8))
        assert(8 == binaryToDecimal(0b1000, 10))
        assert(15 == binaryToDecimal(0b1111, 10))
        assert(16 == binaryToDecimal(0b10000, 5))
    }

    @Test
    fun testSave() {
        assert("3:25" == save(0b0011_011001).apply { println(this) })
        assert("1:00" == save(0b0001_000000).apply { println(this) })
        assert("14:02" == save(0b1110_000010).apply { println(this) })
    }

    @Test
    fun testTurnOn() {
        assert(0b1101 == turnOn(0b101, 3).apply { println(this) })
    }

    @Test
    fun test0() {
        readBinaryWatch(1).apply { println(this) }
    }

    @Test
    fun test1() {
        readBinaryWatch(9).apply { println(this) }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
