import com.abelhu.base.ListNode
import org.junit.Test

//给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
//
// 示例 1: 
//
// 输入: 1->1->2
//输出: 1->2
// 
//
// 示例 2: 
//
// 输入: 1->1->2->3->3
//输出: 1->2->3 
// Related Topics 链表 
// 👍 474 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class Solution83 {
    val set = mutableSetOf<Int>()
    fun deleteDuplicates(head: ListNode?, current: ListNode? = null): ListNode? {
        // 终止条件
        if (head?.next == null) return head
        // 起始条件
        if (current == null) {
            set.add(head.`val`)
            deleteDuplicates(head, head.next)
        } else {
            // 如果已经包含,去掉这个节点,否则继续往下遍历
            if (set.contains(current.`val`)) {
                head.next = current.next
                deleteDuplicates(head, head.next)
            } else {
                set.add(current.`val`)
                deleteDuplicates(head.next, current.next)
            }
        }
        return head
    }


    @Test
    fun test0() = assert(ListNode.output(deleteDuplicates(ListNode.create("1->1->2"))).apply { print("$this\n") } == "1->2")

    @Test
    fun test1() = assert(ListNode.output(deleteDuplicates(ListNode.create("1->1->2->3->3"))).apply { print("$this\n") } == "1->2->3")

    @Test
    fun test2() = assert(ListNode.output(deleteDuplicates(null)).apply { print("$this\n") } == "")

    @Test
    fun test3() = assert(ListNode.output(deleteDuplicates(ListNode.create("1"))).apply { print("$this\n") } == "1")
}
//leetcode submit region end(Prohibit modification and deletion)
