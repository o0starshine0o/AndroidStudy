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


    private fun traverse(right: ListNode?): Boolean {
        // 递归结束条件
        if (right == null) return true
        // 处理节点
        var result = traverse(right.next)
        // [前序|中序]遍历,右侧节点在最后处理
        // 使用位操作
        result = (right.`val` == left?.`val`).and(result)
        // 左侧指针需要移项到下一位
        left = left?.next
        // 返回结果
        return result
    }

    @Test
    fun test() = assert(!isPalindrome(ListNode.create("1->2")))

    @Test
    fun test1() = assert(isPalindrome(ListNode.create("1->2->2->1")))
}
//leetcode submit region end(Prohibit modification and deletion)
