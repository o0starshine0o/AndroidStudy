package com.algorithm.rbtree

import org.junit.Test

class Test {
    private var tree: Tree<Int, String> = Tree()

    init {
        tree.add(Node(100, "100"))
        tree.add(Node(50, "50"))
        tree.add(Node(200, "200"))
        tree.add(Node(125, "125"))
        tree.add(Node(300, "300"))
        tree.delete(50)
    }

    @Test
    fun testTree() {
        println(tree)
    }
}