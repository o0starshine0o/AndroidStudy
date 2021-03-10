package com.abelhu.base

import org.junit.Test

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
    var next: TreeNode? = null

    companion object {
        fun preOrder(root: TreeNode?): String {
            return if (root == null) "" else "${root.`val`},${preOrder(root.left)}${preOrder(root.right)}"
        }

        fun inOrder(root: TreeNode?): String {
            return if (root == null) "" else "${inOrder(root.left)}${root.`val`},${inOrder(root.right)}"
        }

        fun postOrder(root: TreeNode?): String {
            return if (root == null) "" else "${postOrder(root.left)}${postOrder(root.right)}${root.`val`},"
        }

        fun levelOrder(root: TreeNode?) = firstLevelOrder(root).flatten().toList().joinToString { node -> node?.`val`.toString() }

        private fun firstLevelOrder(root: TreeNode?): List<List<TreeNode?>> {
            val result = mutableListOf<List<TreeNode?>>()
            var next: List<TreeNode?>? = listOf(root)
            while (next != null && next.any { item -> item != null }) {
                result.add(next)
                next = secondLevelOrder(next)
            }
            return result
        }

        private fun secondLevelOrder(list: List<TreeNode?>): List<TreeNode?>? {
            if (list.all { item -> item == null }) return null
            val nextLevel = mutableListOf<TreeNode?>()
            list.forEach { item -> nextLevel.addAll(listOf(item?.left, item?.right)) }
            return nextLevel
        }

        fun create(list: Array<Int?>, node: TreeNode? = null, index: Int = 0): TreeNode? {
            // 异常情况
            if (list.isEmpty()) return null
            if (list.first() == null) return null
            if (node == null && index > 0) return null
            // 子树的索引超过数组的长度了,不存在的
            val left = 2 * index + 1
            val right = left + 1
            if (list.size <= right) return null
            // 三个节点赋值
            val root = if (node == null && index == 0) TreeNode(list.first()!!) else node
            list[left]?.apply { root?.left = TreeNode(this) }
            list[right]?.apply { root?.right = TreeNode(this) }
            // 递归处理子节点
            create(list, root?.left, left)
            create(list, root?.right, right)
            // 返回本节点
            return root
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

        assert("0,1,2," == TreeNode.preOrder(root).apply { print("$this\n") })
        assert("1,0,2," == TreeNode.inOrder(root).apply { print("$this\n") })
        assert("1,2,0," == TreeNode.postOrder(root).apply { print("$this\n") })
    }

    @Test
    fun test1() {
        TreeNode.preOrder(TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))).apply { print("$this\n") }
        TreeNode.inOrder(TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))).apply { print("$this\n") }
        TreeNode.postOrder(TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))).apply { print("$this\n") }
        TreeNode.levelOrder(TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))).apply { print("$this\n") }
    }
}