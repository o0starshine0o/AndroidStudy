package com.abelhu.leet

import com.abelhu.base.ListNode
import org.junit.Test

class Code61 {
    fun rotateRight(head: ListNode?, k: Int): ListNode? {
        // step 1: change list to CircularList
        var point = head
        var size = 1
        while (point?.next != null) {
            point = point.next
            size++
        }
        point?.next = head
        // step 2: find the broken position
        for (i in 0 until size - k % size) point = point?.next
        // step 3: record the new head
        val result = point?.next
        // step 4: put the `point` to the tail
        point?.next = null
        // step 5: return result
        return result
    }

    @Test
    fun test0() {
        val head = ListNode(1)
        head.next = ListNode(2)
        rotateRight(head, 0)
    }
}