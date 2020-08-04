package com.algorithm.rbtree

import org.junit.Test

class Test {
    private var tree: Tree<Int, String> = Tree()

    init {
        tree.add(Node(100, "100"))
        tree.add(Node(200, "200"))
        tree.add(Node(150, "150"))
        tree.delete(Int.MAX_VALUE)
    }

    @Test
    fun testTree() {
        println(tree)
    }
}