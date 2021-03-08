import com.abelhu.base.ListNode


//反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
//
// 说明: 
//1 ≤ m ≤ n ≤ 链表长度。 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL, m = 2, n = 4
//输出: 1->4->3->2->5->NULL 
// Related Topics 链表 
// 👍 704 👎 0


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
class Solution92 {
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
// base case
        if (left == 1) {
            return reverseN(head, right)
        }
        // 前进到反转的起点触发 base case
        head?.next = reverseBetween(head?.next, left - 1, right - 1)
        return head
    }

    fun reverse(head: ListNode): ListNode? {
        if (head.next == null) return head
        val last = head.next?.let { reverse(it) }
        head.next?.next = head
        head.next = null
        return last
    }

    private var successor: ListNode? = null // 后驱节点


    // 反转以 head 为起点的 n 个节点，返回新的头结点
    fun reverseN(head: ListNode?, n: Int): ListNode? {
        if (n == 1) {
            // 记录第 n + 1 个节点
            successor = head!!.next
            return head
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        val last = reverseN(head!!.next, n - 1)
        head.next!!.next = head
        // 让反转之后的 head 节点和后面的节点连起来
        head.next = successor
        return last
    }
}
//leetcode submit region end(Prohibit modification and deletion)
