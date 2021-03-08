package com.abelhu.base

class ListNode(var `val`: Int) {
    var next: ListNode? = null

    companion object {
        fun create(text: String): ListNode? {
            val header = ListNode(0)
            var point = header
            val nodes = text.split("->")

            for (node in nodes) {
                val tempNode = ListNode(node.toInt())
                point.next = tempNode
                point = tempNode
            }

            return header.next
        }

        fun output(node: ListNode?) = decode(node).dropLast(2).apply { print(this) }

        private fun decode(node: ListNode?): String {
            return if (node == null) ""
            else "${node.`val`}->${decode(node.next)}"
        }
    }
}