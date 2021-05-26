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
    fun reverseList(head: ListNode?): ListNode? {
        // 终止条件:链表只有一个元素时直接返回
        if (head?.next == null) return head
        // 递归
        // 原先:1->2->3->4
        // 返回:1->2<-3<-4
        // last为4,代表原先的链表尾,新链表的头
        val last = reverseList(head.next)
        // 后续遍历,需要处理根节点
        // 这里纠正1与2的关系,让其反转为1<-2<-3<-4
        head.next?.next = head
        // 清空head的next
        // 因为目前1的next还是为2,所以需要进行清空操作
        head.next = null
        // last作为链表的头
        return last
    }

    fun reverseList1(head: ListNode?):ListNode?{
        // 需要用3个变量,分别记录前一个节点,目前的节点,下一个节点
        // 反转pre和current的关系,并且这3个节点都往后移动
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
