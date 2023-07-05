import org.junit.Assert
import org.junit.Test
import kotlin.math.pow

//给你一个整数 n ，请你在无限的整数序列 [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...] 中找出并返回第 n 位上的数字。
// 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：3
// 
//
// 示例 2： 
//
// 
//输入：n = 11
//输出：0
//解释：第 11 位数字在序列 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... 里是 0 ，它是 10 的一部分。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 2³¹ - 1 
// 
//
// Related Topics 数学 二分查找 👍 380 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution400 {
    fun findNthDigit(n: Int): Int {
        // 1位数的集合: 0..9, 总共9个数, 9位
        // 2位数的集合: 10..99, 总共90个数, 180位
        // 3位数的集合: 100..999, 总共900个数, 2700位
        // 由此构建一个dp集合保存总共有多少位, 虽然最后一个元素为410065408(溢出了), 但是不影响
        // 注意! 如果个数, 到dp[9]的时候就已经到9000000000, 超过了Int.MAX_VALUE, 简单的处理方法就是使用long
        val dp = LongArray(11) { i -> 9 * 10.0.pow(i.toDouble()).toLong() }
        // 计算落在哪个区间内
        var left = n.toLong()
        var i = 0
        // 注意! 判断left的比较对象
        while (left > dp[i] * (i + 1)) {
            left -= (dp[i] * (i + 1))
            i++
        }
        // 所在层每个数的位数
        val count = i + 1
        // 计算商和余数
        // 注意! 通过left-1, 规避了mod是否为0的判断, 这里是我想到的运算技巧
        val mod = (left - 1) % count
        val index = (left - 1) / count

        val num = 10.0.pow(i.toDouble()).toInt() + index
        return num.toString()[mod.toInt()] - '0'
    }

    // 这种做法会超时
    fun findNthDigit0(n: Int): Int {
        // 剩余几位数字
        var left = n
        // 当前整数
        var current = 1
        var currentString = current.toString()

        while (left > currentString.length) {
            left -= currentString.length
            current++
            currentString = current.toString()
        }

        return currentString[left - 1] - '0'
    }

    @Test
    fun test00() {
        // 2147483647, 总共10位
        println(Int.MAX_VALUE)
    }

    @Test
    fun test000() {
        for (i in 1..99) {
            print("$i,")
            if (i % 9 == 0) println()
        }
    }

    @Test
    fun test0() {
        Assert.assertEquals(3, findNthDigit(3))
    }

    @Test
    fun test11() {
        Assert.assertEquals(1, findNthDigit(10))
    }

    @Test
    fun test1() {
        Assert.assertEquals(0, findNthDigit(11))
    }

    @Test
    fun test2() {
        Assert.assertEquals(1, findNthDigit(12))
    }

    @Test
    fun test3() {
        Assert.assertEquals(1, findNthDigit(13))
    }

    @Test
    fun test4() {
        Assert.assertEquals(5, findNthDigit(100))
    }

    @Test
    fun test5() {
        Assert.assertEquals(1, findNthDigit(1000000000))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
