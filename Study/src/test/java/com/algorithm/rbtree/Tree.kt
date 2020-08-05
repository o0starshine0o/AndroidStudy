package com.algorithm.rbtree

import androidx.annotation.IntDef

const val Undefine = Int.MIN_VALUE
const val Left = 0
const val Right = 1

@IntDef(Left, Right, Undefine)
@Retention(AnnotationRetention.SOURCE)
annotation class Branch

class Tree<K : Comparable<K>, V> {
    private var root: Node<K, V>? = null

    /**
     * 获取节点
     */
    fun get(key: K): Node<K, V>? = get(key, root)

    /**
     * 增加节点
     */
    fun add(node: Node<K, V>) = add(node, root)

    /**
     * 删除节点
     */
    fun delete(key: K) {
        // 找到要被删除的节点
        val delete = get(key)
        // 寻找代替节点
        val replace = getReplace(delete)
        // 平衡代替节点
        balance(replace)
        // 用代替节点代替删除节点
        replace(delete, replace)
    }

    /**
     * 使用递归来查找
     */
    private fun get(getKey: K, node: Node<K, V>?): Node<K, V>? {
        node?.apply {
            // 相等，返回这个节点
            if (getKey == key) return node
            // 小于当前节点，递归查找当前节点的左子节点
            if (getKey < key) return get(getKey, left)
            // 大于当前节点，递归查找当前节点的右子节点
            if (getKey > key) return get(getKey, right)
        }
        return null
    }

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
                // 判断父节点是祖节点的哪个branch
                when (getBranch(parent)) {
                    Left -> when (branch) {
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
                    Right -> when (branch) {
                        // 情况4.3.1：插入节点是其父节点的右子节点
                        Right -> {
                            // 父节点设置为黑色，祖节点设置为红色，对祖节点进行左旋
                            parent.color = Black
                            parent.parent?.color = Red
                            rotateLeft(parent.parent)
                        }
                        // 情况4.3.2：插入节点是其父节点的左子节点
                        Left -> {
                            // 对父节点进行右旋，得到4.3.1的情况
                            rotateRight(parent)
                            add(parent, node, Right)
                        }
                    }
                }
            }
        }
    }

    /**
     * 如果要删除此节点，应该先找到对应的替代节点
     */
    private fun getReplace(node: Node<K, V>?) = when {
        node == null -> null
        // 木有子节点，就木有可替换的节点，这边认为替换节点就是自身
        node.left == null && node.right == null -> node
        // 只有一个子节点，那么子节点就是要被替换的节点
        node.left == null && node.right != null -> node.right
        node.left != null && node.right == null -> node.left
        // 如果有2个子节点，需要找到被删除节点的后继节点（大于删除节点的最小节点）
        else -> getNext(node)
    }

    /**
     * 寻找后继节点，大于当前节点的最小节点
     */
    private fun getNext(node: Node<K, V>?): Node<K, V>? {
        var next = node?.right
        while (next?.left != null) next = next.left
        return next
    }

    /**
     * 重新平衡替换节点
     */
    private fun balance(replace: Node<K, V>?) {
        // 情况1：替换节点的颜色是红色，删除了也不会影响平衡，不作处理
        if (replace?.color == Red) return
        // 情况2：替换节点的颜色是黑色，需要通过旋转来平衡树
        when (getBranch(replace)) {
            // 情况2.1：替换节点是其父节点的左子节点
            Left -> {
                val brother = replace?.parent?.right
                if (brother?.color == Red) {
                    // 情况2.1.1：替换节点的兄弟节点是红色（父节点肯定是黑色，性质4，2个子节点一定是黑色）
                    // 将兄节点设为黑色，父节点设为红色，对父节点左旋，得到情况2.1.2.3，然后按照2.1.2.3处理
                    brother.color = Black
                    replace.parent?.color = Red
                    rotateLeft(replace.parent)
                    balance(replace)
                } else if (brother?.color == Black) {
                    // 情况2.1.2：替换节点的兄弟节点是黑色（父节点和子节点的颜色都不确定）
                    if (brother.right?.color == Red) {
                        // 情况2.1.2.1： 兄节点的右子节点是红色
                        // 兄节点颜色设为父节点的颜色，父节点设为黑色，兄节点的右子节点设为黑色，对父节点左旋
                        brother.color = replace.parent?.color ?: Red
                        replace.parent?.color = Black
                        brother.right?.color = Black
                        rotateLeft(replace.parent)
                    } else if (brother.right?.color == Black) {
                        if (brother.left?.color == Red) {
                            // 情况2.1.2.2： 兄节点的左子节点是红色
                            // 将兄节点设为红色，兄节点的左子节点设为黑色，对兄节点做右旋，可以得到2.1.2.1
                            brother.color = Red
                            brother.left?.color = Black
                            rotateRight(brother)
                            // 按照2.1.2.1处理
                            brother.left?.color = replace.parent?.color ?: Red
                            replace.parent?.color = Black
                            brother.color = Black
                            rotateLeft(replace.parent)
                        } else if (brother.left?.color == Black) {
                            // 情况2.1.2.3： 兄节点的左子节点是黑色
                            // 将兄节点设为红色,父节点作为替换跌点,重新处理
                            brother.color = Red
                            balance(replace.parent)
                        }
                    }
                }
            }
            // 情况2.2：替换节点是其父节点的右子节点,刚好和情况2.1相反
            Right -> {
                val brother = replace?.parent?.left
                if (brother?.color == Red) {
                    // 情况2.2.1：替换节点的兄弟节点是红色（父节点肯定是黑色，性质4，2个子节点一定是黑色）
                    // 将兄节点设为黑色，父节点设为红色，对父节点右旋，得到情况2.2.2.3，然后按照2.2.2.3处理
                    brother.color = Black
                    replace.parent?.color = Red
                    rotateRight(replace.parent)
                    balance(replace)
                } else if (brother?.color == Black) {
                    // 情况2.2.2：替换节点的兄弟节点是黑色（父节点和子节点的颜色都不确定）
                    if (brother.left?.color == Red) {
                        // 情况2.2.2.1： 兄节点的左子节点是红色
                        // 兄节点颜色设为父节点的颜色，父节点设为黑色，兄节点的左子节点设为黑色，对父节点右旋
                        brother.color = replace.parent?.color ?: Red
                        replace.parent?.color = Black
                        brother.left?.color = Black
                        rotateRight(replace.parent)
                    } else if (brother.left?.color == Black) {
                        if (brother.right?.color == Red) {
                            // 情况2.2.2.2： 兄节点的右子节点是红色
                            // 将兄节点设为红色，兄节点的右子节点设为黑色，对兄节点做左旋，可以得到2.2.2.1
                            brother.color = Red
                            brother.right?.color = Black
                            rotateLeft(brother)
                            // 按照2.2.2.1处理
                            brother.right?.color = replace.parent?.color ?: Red
                            replace.parent?.color = Black
                            brother.color = Black
                            rotateRight(replace.parent)
                        } else if (brother.right?.color == Black) {
                            // 情况2.1.2.3： 兄节点的左子节点是黑色
                            // 将兄节点设为红色,父节点作为替换跌点,重新处理
                            brother.color = Red
                            balance(replace.parent)
                        }
                    }
                }
            }
        }
    }

    /**
     * 用替换节点替换删除节点
     */
    private fun replace(delete: Node<K, V>?, replace: Node<K, V>?) {
        // 如果要用自身替换自身，意味着要删除自身
        val node = if (delete == replace) null else replace
        node?.left = delete?.left
        node?.right = delete?.right
        node?.parent = delete?.parent
        node?.color = delete?.color ?: Red
        when {
            delete?.parent?.left == delete -> delete?.parent?.left = node
            delete?.parent?.right == delete -> delete?.parent?.right = node
        }
    }

    /**
     * 获取叔节点
     */
    private fun getUncle(node: Node<K, V>) = when (node.parent) {
        node.parent?.parent?.left -> node.parent?.parent?.right
        node.parent?.parent?.right -> node.parent?.parent?.left
        else -> null
    }

    /**
     * 判断当前节点位于父节点的哪个分支上
     */
    private fun getBranch(node: Node<K, V>?) = when (node) {
        node?.parent?.left -> Left
        node?.parent?.right -> Right
        else -> Undefine
    }

    /**
     * 左旋
     */
    private fun rotateLeft(node: Node<K, V>?) {
        // 记录下父节点
        val parent = node?.parent
        // 记录右子节点
        val right = node?.right
        // 旋转后，当前节点的右子节点变成了原先右子节点的左子节点
        node?.right = right?.left
        right?.left?.parent = node
        // 旋转后，原先右子节点的左子节点变成了当前节点
        right?.left = node
        node?.parent = right
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