import org.junit.Assert
import org.junit.Test

//设计实现双端队列。
//
// 实现 MyCircularDeque 类: 
//
// 
// MyCircularDeque(int k) ：构造函数,双端队列最大为 k 。 
// boolean insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true ，否则返回 false 。 
// boolean insertLast() ：将一个元素添加到双端队列尾部。如果操作成功返回 true ，否则返回 false 。 
// boolean deleteFront() ：从双端队列头部删除一个元素。 如果操作成功返回 true ，否则返回 false 。 
// boolean deleteLast() ：从双端队列尾部删除一个元素。如果操作成功返回 true ，否则返回 false 。 
// int getFront() )：从双端队列头部获得一个元素。如果双端队列为空，返回 -1 。 
// int getRear() ：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1 。 
// boolean isEmpty() ：若双端队列为空，则返回 true ，否则返回 false 。 
// boolean isFull() ：若双端队列满了，则返回 true ，否则返回 false 。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入
//["MyCircularDeque", "insertLast", "insertLast", "insertFront", "insertFront", 
//"getRear", "isFull", "deleteLast", "insertFront", "getFront"]
//[[3], [1], [2], [3], [4], [], [], [], [4], []]
//输出
//[null, true, true, true, false, 2, true, true, true, 4]
//
//解释
//MyCircularDeque circularDeque = new MycircularDeque(3); // 设置容量大小为3
//circularDeque.insertLast(1);			        // 返回 true
//circularDeque.insertLast(2);			        // 返回 true
//circularDeque.insertFront(3);			        // 返回 true
//circularDeque.insertFront(4);			        // 已经满了，返回 false
//circularDeque.getRear();  				// 返回 2
//circularDeque.isFull();				        // 返回 true
//circularDeque.deleteLast();			        // 返回 true
//circularDeque.insertFront(4);			        // 返回 true
//circularDeque.getFront();				// 返回 4
//  
//
// 
//
// 提示： 
//
// 
// 1 <= k <= 1000 
// 0 <= value <= 1000 
// insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty,
// isFull 调用次数不大于 2000 次 
// 
//
// Related Topics 设计 队列 数组 链表 👍 215 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class MyCircularDeque(val k: Int) {
    // 目前使用的容量
    private var used = 0

    // 链表头
    private var header: Item? = null

    // 链表尾
    private var tail: Item? = null

    fun insertFront(value: Int): Boolean {
        if (isFull()) return false
        val item = Item(value).apply { next = header }
        header?.pre = item
        header = item
        // 注意! 原本为空, tail也需要处理
        if (isEmpty()) tail = item
        used++
        return true
    }

    fun insertLast(value: Int): Boolean {
        if (isFull()) return false
        val item = Item(value).apply { pre = tail }
        tail?.next = item
        tail = item
        // 注意! 原本为空, header也需要处理
        if (isEmpty()) header = item
        used++
        return true
    }

    fun deleteFront(): Boolean {
        if (isEmpty()) return false
        header = header?.next
        // 注意! 删除头部, 其第二个的pre也需要处理
        header?.pre = null
        used--
        // 注意! 如果删空了, tail也需要处理
        if (isEmpty()) tail = null
        return true
    }

    fun deleteLast(): Boolean {
        if (isEmpty()) return false
        tail = tail?.pre
        // 注意! 删除末尾, 其倒数第二个的next也需要处理
        tail?.next = null
        used--
        // 注意! 如果删空了, header也要处理
        if (isEmpty()) header = null
        return true
    }

    fun getFront(): Int {
        return header?.value ?: -1
    }

    fun getRear(): Int {
        return tail?.value ?: -1
    }

    fun isEmpty(): Boolean {
        return used == 0
    }

    fun isFull(): Boolean {
        return used == k
    }

    private class Item(val value: Int) {
        var pre: Item? = null
        var next: Item? = null

        override fun toString(): String {
            return value.toString()
        }
    }

}

class TestMyCircularDeque {

    @Test
    fun testMyCircularDeque() {
        val circularDeque = MyCircularDeque(3)
        Assert.assertEquals(true, circularDeque.insertLast(1))
        Assert.assertEquals(true, circularDeque.insertLast(2))
        Assert.assertEquals(true, circularDeque.insertFront(3))
        Assert.assertEquals(false, circularDeque.insertFront(4))
        Assert.assertEquals(2, circularDeque.getRear())
        Assert.assertEquals(true, circularDeque.isFull())
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(true, circularDeque.insertFront(4))
        Assert.assertEquals(4, circularDeque.getFront())
    }

    @Test
    fun testMyCircularDeque1() {
        // ["MyCircularDeque","insertFront","getRear","insertFront","getRear","insertLast","getFront","getRear","getFront","insertLast","deleteLast","getFront"]
        // [[3],[9],[],[9],[],[5],[],[],[],[8],[],[]]
        // [null,true,9,true,9,true,9,5,9,false,true,9]
        val circularDeque = MyCircularDeque(3)
        Assert.assertEquals(true, circularDeque.insertFront(9))
        Assert.assertEquals(9, circularDeque.getRear())
        Assert.assertEquals(true, circularDeque.insertFront(9))
        Assert.assertEquals(9, circularDeque.getRear())
        Assert.assertEquals(true, circularDeque.insertLast(5))
        Assert.assertEquals(9, circularDeque.getFront())
        Assert.assertEquals(5, circularDeque.getRear())
        Assert.assertEquals(9, circularDeque.getFront())
        Assert.assertEquals(false, circularDeque.insertLast(8))
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(9, circularDeque.getFront())
    }

    @Test
    fun testMyCircularDeque2() {
        // ["MyCircularDeque","insertFront","deleteLast","getRear","getFront","getFront","deleteFront","insertFront","insertLast","insertFront","getFront","insertFront"]
        // [[4],[9],[],[],[],[],[],[6],[5],[9],[],[6]]
        // [null,true,true,-1,-1,-1,false,true,true,true,9,true]
        val circularDeque = MyCircularDeque(4)
        Assert.assertEquals(true, circularDeque.insertFront(9))
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(-1, circularDeque.getRear())
        Assert.assertEquals(-1, circularDeque.getFront())
        Assert.assertEquals(-1, circularDeque.getFront())
        Assert.assertEquals(false, circularDeque.deleteFront())
        Assert.assertEquals(true, circularDeque.insertFront(6))
        Assert.assertEquals(true, circularDeque.insertLast(5))
        Assert.assertEquals(true, circularDeque.insertFront(9))
        Assert.assertEquals(9, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.insertFront(6))
    }

    @Test
    fun testMyCircularDeque3() {
        val circularDeque = MyCircularDeque(52)
        Assert.assertEquals(true, circularDeque.insertFront(80))
        Assert.assertEquals(80, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.insertFront(27))
        Assert.assertEquals(27, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(true, circularDeque.insertFront(60))
        Assert.assertEquals(true, circularDeque.insertFront(81))
        Assert.assertEquals(27, circularDeque.getRear())
    }

    @Test
    fun testMyCircularDeque4() {
        val circularDeque = MyCircularDeque(52)
        Assert.assertEquals(true, circularDeque.insertFront(80))
        Assert.assertEquals(80, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.insertFront(27))
        Assert.assertEquals(27, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(true, circularDeque.insertFront(60))
        Assert.assertEquals(true, circularDeque.insertFront(81))
        Assert.assertEquals(27, circularDeque.getRear())
    }

    @Test
    fun testMyCircularDeque5() {
        val circularDeque = MyCircularDeque(789)
        Assert.assertEquals(true, circularDeque.insertFront(332))
        Assert.assertEquals(332, circularDeque.getRear())
        Assert.assertEquals(true, circularDeque.insertFront(68))
        Assert.assertEquals(true, circularDeque.deleteLast())
        Assert.assertEquals(68, circularDeque.getRear())
        Assert.assertEquals(false, circularDeque.isFull())
        Assert.assertEquals(68, circularDeque.getFront())
        Assert.assertEquals(true, circularDeque.deleteFront())
        Assert.assertEquals(true, circularDeque.isEmpty())
        Assert.assertEquals(-1, circularDeque.getRear())
        Assert.assertEquals(-1, circularDeque.getFront())
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * var obj = MyCircularDeque(k)
 * var param_1 = obj.insertFront(value)
 * var param_2 = obj.insertLast(value)
 * var param_3 = obj.deleteFront()
 * var param_4 = obj.deleteLast()
 * var param_5 = obj.getFront()
 * var param_6 = obj.getRear()
 * var param_7 = obj.isEmpty()
 * var param_8 = obj.isFull()
 */
//leetcode submit region end(Prohibit modification and deletion)
