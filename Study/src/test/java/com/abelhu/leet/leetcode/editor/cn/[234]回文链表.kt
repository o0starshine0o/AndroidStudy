import com.abelhu.base.ListNode
import org.junit.Test

//请判断一个链表是否为回文链表。
//
// 示例 1: 
//
// 输入: 1->2
//输出: false 
//
// 示例 2: 
//
// 输入: 1->2->2->1
//输出: true
// 
//
// 进阶： 
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 链表 双指针 
// 👍 888 👎 0


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
class Solution234 {
    var left: ListNode? = null
    fun isPalindrome(head: ListNode?): Boolean {
        left = head
        return traverse(head)
    }


    /**
     * 模仿了一个栈结构,FILO
     */
    private fun traverse(right: ListNode?): Boolean {
        // 递归结束条件
        if (right == null) return true
        // 处理节点
        // 先处理节点,意味着后续遍历
        var result = traverse(right.next)
        // [前序|中序]遍历,右侧节点在最后处理
        // 使用位操作
        result = (right.`val` == left?.`val`).and(result)
        // 出栈的时候,不仅要出栈,与之对应的左侧指针需要移项到下一位
        // 所以原本可以O(N/2)的判断必须要变成O(N)的时间复杂度
        left = left?.next
        // 返回结果
        return result
    }

    // 如果需要把时间复杂度降为N/2,需要先用快慢指针找到中点,然后把右侧的链表进行入栈操作,再判断
    // 如果需要把空间复杂度降为O(1),需要先用快慢指针找到中点,然后进行翻转链表操作,再判断
    // 如果还需要恢复链表,需要把反转的链表再次反转一遍

    @Test
    fun test() = assert(!isPalindrome(ListNode.create("1->2")))

    @Test
    fun test1() = assert(isPalindrome(ListNode.create("1->2->2->1")))
}
//leetcode submit region end(Prohibit modification and deletion)
