import org.junit.Test

//珂珂喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
//
// 珂珂可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后
//这一小时内不会再吃更多的香蕉。 
//
// 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。 
//
// 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。 
//
// 
//
// 
// 
//
// 示例 1： 
//
// 输入: piles = [3,6,7,11], H = 8
//输出: 4
// 
//
// 示例 2： 
//
// 输入: piles = [30,11,23,4,20], H = 5
//输出: 30
// 
//
// 示例 3： 
//
// 输入: piles = [30,11,23,4,20], H = 6
//输出: 23
// 
//
// 
//
// 提示： 
//
// 
// 1 <= piles.length <= 10^4 
// piles.length <= H <= 10^9 
// 1 <= piles[i] <= 10^9 
// 
// Related Topics 二分查找 
// 👍 162 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution875 {
    fun minEatingSpeed(piles: IntArray, h: Int, min: Int = 1, max: Int = piles.maxOrNull() ?: Int.MAX_VALUE): Int {
        for (speed in min..max)
            if (piles.sumBy { pile -> kotlin.math.ceil(pile / speed.toFloat()).toInt() } <= h) return speed
        return max
    }

    fun minEatingSpeeda(piles: IntArray, hour: Int): Int {
        var left = 1
        var right = piles.maxOrNull() ?: return -1
        // 寻求最小值,需要左闭右开区间
        while (left < right) {
            // 寻找中间值
            val mid = left + (right - left) / 2
            // 二分查找(注意,这里如果使用float会导致精度丢失,原本应该是2.0000000030000000被压缩为2.0,最终计算错误)
            when (piles.sumBy { pile -> kotlin.math.ceil(pile / mid.toDouble()).toInt() }) {
                // 当前数据符合要求,但是希望找到更大(通过计算更大,意味着这里取值要更小)符合要求的值,因为mid已经测试过了,所以不需要包含mid
                hour -> right = mid
                // 当前数据符合要求,但是希望找到更大(通过计算更大,意味着这里取值要更小)符合要求的值,因为mid已经测试过了,所以不需要包含mid
                in Int.MIN_VALUE until hour -> right = mid
                // 当前数据不符合要求了,需要找到更小(通过计算更小,意味着这里取值要更大)的符合要求的值,因为右侧是开区间,所以使用mid,同mid+1相比,缩小了一位查找区间
                in hour until Int.MAX_VALUE -> left = mid + 1
                // 其他情况,照理说不存在
                else -> right = mid
            }
        }
        // 因为结束的条件时left == right,所以这里随便返回
        return right
    }

    @Test
    fun test0() {
        assert(4 == minEatingSpeed(intArrayOf(3, 6, 7, 11), 8).apply { println(this) })
    }

    @Test
    fun test1() {
        assert(30 == minEatingSpeed(intArrayOf(30, 11, 23, 4, 20), 5).apply { println(this) })
    }

    @Test
    fun test2() {
        assert(23 == minEatingSpeed(intArrayOf(30, 11, 23, 4, 20), 6).apply { println(this) })
    }

    @Test
    fun test0a() {
        assert(4 == minEatingSpeeda(intArrayOf(3, 6, 7, 11), 8).apply { println(this) })
    }

    @Test
    fun test1a() {
        assert(30 == minEatingSpeeda(intArrayOf(30, 11, 23, 4, 20), 5).apply { println(this) })
    }

    @Test
    fun test2a() {
        assert(23 == minEatingSpeeda(intArrayOf(30, 11, 23, 4, 20), 6).apply { println(this) })
    }

    @Test
    fun test3a() {
        assert(3 == minEatingSpeeda(intArrayOf(5), 2).apply { println(this) })
    }

    @Test
    fun test4a() {
        assert(500000000 == minEatingSpeeda(intArrayOf(1000000000), 2).apply { println(this) })
    }

    @Test
    fun test5a() {
        assert(50000000 == minEatingSpeeda(intArrayOf(100000000), 2).apply { println(this) })
    }

    @Test
    fun test6a() {
        assert(5000000 == minEatingSpeeda(intArrayOf(10000000), 2).apply { println(this) })
    }

    @Test
    fun test7a() {
        assert(500000 == minEatingSpeeda(intArrayOf(1000000), 2).apply { println(this) })
    }

    @Test
    fun test8a() {
        assert(50000 == minEatingSpeeda(intArrayOf(100000), 2).apply { println(this) })
    }

    @Test
    fun test9a() {
        assert(5000 == minEatingSpeeda(intArrayOf(10000), 2).apply { println(this) })
    }

    @Test
    fun test10a() {
        assert(500 == minEatingSpeeda(intArrayOf(1000), 2).apply { println(this) })
    }

    @Test
    fun test11a() {
        assert(50 == minEatingSpeeda(intArrayOf(100), 2).apply { println(this) })
    }

    @Test
    fun test12a() {
        assert(5 == minEatingSpeeda(intArrayOf(10), 2).apply { println(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
