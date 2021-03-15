import com.abelhu.base.TreeNode
import org.junit.Test

//根据一棵树的前序遍历与中序遍历构造二叉树。
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
// Related Topics 树 深度优先搜索 数组 
// 👍 918 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution105 {
    fun buildTree(
        preOrder: IntArray,
        inOrder: IntArray,
        preStart: Int = 0,
        preEnd: Int = preOrder.size - 1,
        inStart: Int = 0,
        inEnd: Int = inOrder.size - 1
    ): TreeNode? {
        // 异常处理
        if (preOrder.isEmpty()) return null
        if (inOrder.isEmpty()) return null
        if (preEnd < preStart) return null
        if (inEnd < inStart) return null
        // 结束条件
        if (preEnd == preStart) return TreeNode(preOrder[preStart])
        if (inEnd == inStart) return TreeNode(inOrder[inStart])
        // ---------先序遍历的起点----------
        // 先序遍历的第一个就是根节点
        val root = TreeNode(preOrder[preStart])
        // 找到根节点在中序遍历中的位置
        val rootInOrder = inOrder.indexOf(root.`val`) // 3
        // 位于rootInOrder左侧的就是新左子树的中序遍历起点
        val newInStartLeft = inStart // 0
        val newInEndLeft = rootInOrder - 1 // 2
        // 位于rootInOrder左侧的就是新右子树的中序遍历起点
        val newInStartRight = rootInOrder + 1 // 4
        val newInEndRight = inEnd // 6
        // 前序遍历的子节点长度与中序遍历的节点长度一致
        val newPreStartLeft = preStart + 1 // 1
        val newPreEndLeft = newPreStartLeft + newInEndLeft - newInStartLeft // 3
        // 前序遍历的子节点长度与中序遍历的节点长度一致
        val newPreStartRight = newPreEndLeft + 1 // 4
        val newPreEndRight = newPreStartRight + newInEndRight - newInStartRight // 6

        // 做递归遍历
        // 左子树:
        root.left = buildTree(preOrder, inOrder, newPreStartLeft, newPreEndLeft, newInStartLeft, newInEndLeft)
        // 右子树:
        root.right = buildTree(preOrder, inOrder, newPreStartRight, newPreEndRight, newInStartRight, newInEndRight)

        // 返回根节点
        return root
    }

    @Test
    fun test() {
        val origin = TreeNode.inOrder(TreeNode.create(arrayOf(3, 9, 20, null, null, 15, 7))).apply { print("$this\n") }
        val result = TreeNode.inOrder(buildTree(intArrayOf(3, 9, 20, 15, 7), intArrayOf(9, 3, 15, 20, 7))).apply { print("$this\n") }
        assert(origin == result)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
