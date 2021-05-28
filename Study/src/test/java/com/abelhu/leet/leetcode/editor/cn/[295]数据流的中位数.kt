import org.junit.Test
import java.util.*

//中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
//
// 例如， 
//
// [2,3,4] 的中位数是 3 
//
// [2,3] 的中位数是 (2 + 3) / 2 = 2.5 
//
// 设计一个支持以下两种操作的数据结构： 
//
// 
// void addNum(int num) - 从数据流中添加一个整数到数据结构中。 
// double findMedian() - 返回目前所有元素的中位数。 
// 
//
// 示例： 
//
// addNum(1)
//addNum(2)
//findMedian() -> 1.5
//addNum(3) 
//findMedian() -> 2 
//
// 进阶: 
//
// 
// 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？ 
// 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？ 
// 
// Related Topics 堆 设计 
// 👍 420 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class MedianFinder() {

    /**
     * 使用2个优先队列来保存数据
     * 优先队列的数据结构是无界数组,逻辑结构是完全二叉树
     * small存放较小的那一堆数据,采用的是大顶堆(大数位于树根)
     * large存放较大的那一堆数据,采用的是小顶堆(小数位于树根)(PriorityQueue默认就是小顶堆)
     */
    private val small = PriorityQueue<Int>() { a, b -> b - a }
    private val large = PriorityQueue<Int>()

    fun addNum(num: Int) {
        if (small.size > large.size) {
            small.offer(num)
            large.offer(small.poll())
        } else {
            large.offer(num)
            small.offer(large.poll())
        }
    }

    fun findMedian(): Double {
        return when {
            small.size > large.size -> small.peek()?.toDouble() ?: 0.0
            small.size < large.size -> large.peek()?.toDouble() ?: 0.0
            else -> ((small.peek() ?: 0) + (large.peek() ?: 0)) / 2.0
        }
    }

    @Test
    fun test0() {
        addNum(1)
        addNum(2)
        assert(findMedian() - 1.5 < 0.000_001)
        addNum(3)
        assert(findMedian() - 2 < 0.000_001)
    }

}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * var obj = MedianFinder()
 * obj.addNum(num)
 * var param_2 = obj.findMedian()
 */
//leetcode submit region end(Prohibit modification and deletion)
