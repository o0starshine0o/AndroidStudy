package com.abelhu.base

import org.junit.Test

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
    var next: TreeNode? = null

    companion object {
        fun preOrder(root: TreeNode?): String {
            return if (root == null) "" else "${root.`val`}${preOrder(root.left)}${preOrder(root.right)}"
        }

        fun inOrder(root: TreeNode?): String {
            return if (root == null) "" else "${inOrder(root.left)}${root.`val`}${inOrder(root.right)}"
        }

        fun postOrder(root: TreeNode?): String {
            return if (root == null) "" else "${postOrder(root.left)}${postOrder(root.right)}${root.`val`}"
        }
    }

    override fun toString() = `val`.toString()
}

class TestTreeNode {

    @Test
    fun test0() {
        val root = TreeNode(0)
        root.left = TreeNode(1)
        root.right = TreeNode(2)

        print("${TreeNode.preOrder(root)}\n")
        print("${TreeNode.inOrder(root)}\n")
        print("${TreeNode.postOrder(root)}\n")
    }
}