import com.abelhu.base.ListNode
import org.junit.Test

//反转一个单链表。
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 进阶: 
//你可以迭代或递归地反转链表。你能否用两种方法解决这道题？ 
// Related Topics 链表 
// 👍 1558 👎 0


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
class Solution206 {
    private fun reverseList(head: ListNode?): ListNode? {
        // 终止条件
        if (head?.next == null) return head
        // 递归
        val result = reverseList(head.next)
        // 后续遍历,需要处理根节点
        head.next?.next = head
        // 清空head的next
        head.next = null
        // result作为链表的头
        return result
    }


    private fun reverseList1(head: ListNode?):ListNode?{
        var pre = head
        var current = head?.next
        var next = current?.next
        // 注意,这个不要放到循环里面去,只有第一次的时候需要清空
        pre?.next = null

        while (current!=null){
            current.next = pre
            pre = current
            current = next
            next = next?.next
        }

        return pre
    }

    @Test
    fun test() = assert("5->4->3->2->1" == ListNode.output(reverseList(ListNode.create("1->2->3->4->5"))))

    @Test
    fun test1() = assert("5->4->3->2->1" == ListNode.output(reverseList1(ListNode.create("1->2->3->4->5"))))
}
//leetcode submit region end(Prohibit modification and deletion)
