package com.abelhu.leet

import com.abelhu.base.ListNode
import org.junit.Test

class Code234 {
    private fun isPalindrome(head: ListNode?): Boolean {
        var slow = head
        var fast = head
        // 切分列表的中点node
        var guard: ListNode? = null
        // 快慢指针寻找中点，并且反转慢指针前面的列表
        while (fast?.next != null) {
            fast = fast.next!!.next
            val temp = guard
            guard = slow
            slow = slow?.next
            guard?.next = temp
        }
        // 判断链表的奇偶性
        if (fast != null) {
            slow = slow?.next
        }
        while (slow != null) {
            if (slow.`val` != guard?.`val`) return false
            slow = slow.next
            guard = guard.next
        }
        return true
    }

    @Test
    fun test0() {
        var node: ListNode? = null
        val head = node
        for (i in intArrayOf(1, 2, 2, 1)) {
            if (node == null) {
                node = ListNode(i)
                continue
            }
            node.next = ListNode(i)
            node = node.next
        }
        assert(isPalindrome(head))
    }

    @Test
    fun test1() {
        var node: ListNode? = null
        val head = node
        for (i in intArrayOf(1, 3, 1)) {
            if (node == null) {
                node = ListNode(i)
                continue
            }
            node.next = ListNode(i)
            node = node.next
        }
        assert(isPalindrome(head))
    }

    @Test
    fun test2() {
        var node: ListNode? = null
        val head = node
        for (i in intArrayOf(1, 2, 3, 1)) {
            if (node == null) {
                node = ListNode(i)
                continue
            }
            node.next = ListNode(i)
            node = node.next
        }
        assert(isPalindrome(head))
    }
}