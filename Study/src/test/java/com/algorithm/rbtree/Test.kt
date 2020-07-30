package com.algorithm.rbtree

import org.junit.Test

class Test {
    private var tree: Tree<Int, String> = Tree()

    init {
        tree.add(Node(100, "100"))
        tree.add(Node(23, "23"))
        tree.add(Node(233, "233"))
        tree.add(Node(24, "24"))
    }

    @Test
    fun testTree() {
        println(tree)
    }
}