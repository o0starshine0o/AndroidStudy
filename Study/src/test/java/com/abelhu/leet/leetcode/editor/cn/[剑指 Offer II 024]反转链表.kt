import com.abelhu.base.ListNode
import org.junit.Test

//给定单链表的头节点 head ，请反转链表，并返回反转后的链表的头节点。
//
// 
// 
// 
// 
// 
//
// 示例 1： 
// 
// 
//输入：head = [1,2,3,4,5]
//输出：[5,4,3,2,1]
// 
//
// 示例 2： 
// 
// 
//输入：head = [1,2]
//输出：[2,1]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目范围是 [0, 5000] 
// -5000 <= Node.val <= 5000 
// 
//
// 
//
// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？ 
//
// 
//
// 
// 注意：本题与主站 206 题相同： https://leetcode-cn.com/problems/reverse-linked-list/ 
//
// Related Topics 递归 链表 👍 145 👎 0


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
class Solution024 {
    private fun reverseList(head: ListNode?): ListNode? {
        var parent: ListNode? = null
        var current = head
        while (current != null) {
            // 作为下一个的记录
            val next = current.next

            // 反转列表
            current.next = parent

            // 移动指针
            parent = current
            current = next

        }

        return parent
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
