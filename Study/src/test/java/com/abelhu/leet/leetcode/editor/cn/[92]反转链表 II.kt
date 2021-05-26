import com.abelhu.base.ListNode
import org.junit.Test


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
    // 后驱节点
    private var successor: ListNode? = null

    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        // 结束条件:找到了需要反转的位置
        if (left == 1) return reverseN(head, right)
        // 递归调用,注意,每次调用,双指针都要同步移动
        val result = reverseBetween(head?.next, left - 1, right - 1)
        // 处理根节点
        head?.next = result
        // 返回最开始的根节点
        return head
    }

    fun reverseN(head: ListNode?, count: Int): ListNode? {
        // 结束条件
        if (count == 1) {
            // 保存后继节点
            successor = head?.next
            // 只有这一个节点,所以可以直接返回
            return head
        }
        // 递归求解
        val last = reverseN(head?.next, count - 1)
        // 处理根节点
        head?.next?.next = head
        // 这里是核心,需要把不用反转的节点记录下来,否则没法连接起来
        // 注意,这里需要把最后一个被"反转"的节点的子节点设置为后继,以便和其他未"反转"的节点连起来
        head?.next = successor
        // 返回处理结果
        return last
    }

    @Test
    fun test0() = assert("4->3->2->1->5" == ListNode.output(reverseN(ListNode.create("1->2->3->4->5"), 4)))

    @Test
    fun test1() = assert("1->4->3->2->5" == ListNode.output(reverseBetween(ListNode.create("1->2->3->4->5"), 2, 4)))
}
//leetcode submit region end(Prohibit modification and deletion)
