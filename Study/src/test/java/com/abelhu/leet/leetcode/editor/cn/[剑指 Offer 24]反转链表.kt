import com.abelhu.base.ListNode
import org.junit.Test

//定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
//
// 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 5000 
//
// 
//
// 注意：本题与主站 206 题相同：https://leetcode-cn.com/problems/reverse-linked-list/ 
//
// Related Topics 递归 链表 👍 599 👎 0


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
class Solution24 {
    private var parent: ListNode? = null

    private fun reverseList(head: ListNode?): ListNode? {
        // base case
        if (head == null) return parent

        val next = head.next
        // 注意, 节点不需要2头都处理, 只需要处理一端就可以了
//        head.next?.next = head
        head.next = parent
        parent = head

        return reverseList(next)
    }

    @Test
    fun test0() {
        val head = ListNode(1)
        head.next = ListNode(2)
        head.next?.next = ListNode(3)
        head.next?.next?.next = ListNode(4)
        head.next?.next?.next?.next = ListNode(5)

        var result = reverseList(head)
        while (result != null) {
            println(result.`val`)
            result = result.next
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
