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
    fun deleteDuplicates(head: ListNode?): ListNode? {
        if (set.contains(head?.`val`)) {
            head?.next = head?.next?.next
            deleteDuplicates(head?.next)
        }
        return head
    }


    @Test
    fun test0() = print(ListNode.output(ListNode.create("1->1->2->3->3")))
//    @Test
//    fun test0() = assert(deleteDuplicates("aa") == 0)
}
//leetcode submit region end(Prohibit modification and deletion)
