package com.algorithm.rbtree

import androidx.annotation.IntDef

const val Left = 0
const val Right = 1

@IntDef(Left, Right)
@Retention(AnnotationRetention.SOURCE)
annotation class Branch

class Tree<K : Comparable<K>, V> {
    private var root: Node<K, V>? = null

    fun get(key: K): V? = get(key, root)

    /**
     * 使用递归来查找
     */
    private fun get(getKey: K, node: Node<K, V>?): V? {
        node?.apply {
            // 相等，返回这个节点
            if (getKey == key) return value
            // 小于当前节点，递归查找当前节点的左子节点
            if (getKey < key) return get(getKey, left)
            // 大于当前节点，递归查找当前节点的右子节点
            if (getKey > key) return get(getKey, right)
        }
        return null
    }

    /**
     * 公开的方法，给其他地方使用
     */
    fun add(node: Node<K, V>) = add(node, root)

    /**
     * 通过递归的方式，先完成查找二叉树的构建
     */
    private fun add(node: Node<K, V>, parent: Node<K, V>?) {
        // 情况1：如果木有根节点，把插入节点作为根节点，颜色设为黑色
        if (parent == null) add(node, null, Left)
        // 情况2：如果插入的节点已经存在，更新当前节点
        // 之所以不使用直接修改值的方式，是因为会遇到递归自己的情况
        else if (node.key == parent.key) node.parent = node.apply { color = parent.color }
        // 直接赋值给左子树，或者递归左子树
        else if (node.key < parent.key) if (parent.left == null) add(node, parent, Left) else add(node, parent.left)
        // 直接赋值给右子树，或者递归右子树
        else if (node.key > parent.key) if (parent.right == null) add(node, parent, Right) else add(node, parent.right)
    }

    /**
     * 添加子节点后需要旋转、变色等
     */
    private fun add(node: Node<K, V>, parent: Node<K, V>?, @Branch branch: Int) {
        // 如果parent为空，表示node已经是root了，需要把node的颜色设置为黑色（这是唯一一种增加黑高的方式）
        if (parent == null) {
            root = node.apply { color = Black };return
        }
        // 先把要插入的节点放到对应的位置上去
        when (branch) {
            Left -> parent.left = node.apply { this.parent = parent }
            Right -> parent.right = node.apply { this.parent = parent }
        }
        // 情况3： 插入节点的父节点是黑色，直接怼上去就好了，不会影响黑高
        if (parent.color == Black) return
        // 情况4： 插入节点的父节点是红色
        // 根据性质4，因为父节点的颜色是红色，祖节点一定存在，且为黑色，因为祖节点如果是红色，那么父节点一定是黑色，和已知条件相悖
        else {
            // 情况4.1： 叔节点存在，且为红色，把父节点和叔节点变成黑色，祖节点变成红色
            if (getUncle(node)?.color == Red) {
                node.parent?.color = Black
                getUncle(node)?.color = Black
                node.parent?.parent?.apply {
                    color = Red
                    // 把祖节点当做插入节点，继续进行执行插入操作
                    add(this, this.parent, Left)
                }
            }
            // 情况4.2：叔节点不存在，或者为黑色
            // 如果叔节点为黑色，那么一定是叶子节点null，否则黑高就不满足了
            else {
                when (branch) {
                    // 情况4.2.1：插入节点是其父节点的左子节点
                    Left -> {
                        // 父节点设置为黑色，祖节点设置为红色，对祖节点进行右旋
                        parent.color = Black
                        parent.parent?.color = Red
                        rotateRight(parent.parent)
                    }
                    // 情况4.2.2：插入节点是其父节点的右子节点
                    Right -> {
                        // 对父节点进行左旋，得到4.2.1的情况
                        rotateLeft(parent)
                        add(parent, node, Left)
                    }
                }
            }
        }
    }

    /**
     * 获取叔节点
     */
    private fun getUncle(node: Node<K, V>): Node<K, V>? {
        return when (node.parent) {
            node.parent?.parent?.left -> node.parent?.parent?.right
            node.parent?.parent?.right -> node.parent?.parent?.left
            else -> null
        }
    }

    /**
     * 左旋
     */
    private fun rotateLeft(node: Node<K, V>) {
        // 记录下父节点
        val parent = node.parent
        // 记录右子节点
        val right = node.right
        // 旋转后，当前节点的右子节点变成了原先右子节点的左子节点
        node.right = right?.left
        right?.left?.parent = node
        // 旋转后，原先右子节点的左子节点变成了当前节点
        right?.left = node
        node.parent = right
        // 旋转后，原先的右子节点变成了根节点，需要判断node位于父节点的哪个分支上
        when {
            parent == null -> root = right
            parent.left == node -> parent.left = right
            parent.right == node -> parent.right = right
        }
        right?.parent = parent
    }

    /**
     * 右旋
     */
    private fun rotateRight(node: Node<K, V>?) {
        // 记录下父节点
        val parent = node?.parent
        // 记录左子节点
        val left = node?.left
        // 旋转后，当前节点的左子节点变成了原先左子节点的右子节点
        node?.left = left?.right
        left?.right?.parent = node
        // 旋转后，原先左子节点的右子节点变成了当前节点
        left?.right = node
        node?.parent = left
        // 旋转后，原先的右子节点变成了根节点，需要判断node位于父节点的哪个分支上
        when {
            parent == null -> root = left
            parent.left == node -> parent.left = left
            parent.right == node -> parent.right = left
        }
        left?.parent = parent

    }
}