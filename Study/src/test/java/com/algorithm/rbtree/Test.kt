package com.algorithm.rbtree

import org.junit.Test

class Test {
    private var tree: Tree<Int, String> = Tree()

    init {
        tree.add(Node(100, "100"))
        tree.add(Node(50, "50"))
        tree.add(Node(1, "1"))
    }

    @Test
    fun testTree() {
        println(tree)
    }
}