import com.abelhu.base.TreeNode
import org.junit.Test

//根据一棵树的中序遍历与后序遍历构造二叉树。
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
// Related Topics 树 深度优先搜索 数组 
// 👍 462 👎 0


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
class Solution106 {
    fun buildTree(
        inOrder: IntArray,
        postOrder: IntArray,
        inStart: Int = 0,
        inEnd: Int = inOrder.size - 1,
        postStart: Int = 0,
        postEnd: Int = postOrder.size - 1
    ): TreeNode? {
        // 异常处理
        if (inOrder.isEmpty()) return null
        if (postOrder.isEmpty()) return null
        if (inEnd < inStart) return null
        if (postEnd < postStart) return null
        // 结束条件
        if (inEnd == inStart) return TreeNode(inOrder[inStart])
        if (postEnd == postStart) return TreeNode(postOrder[postStart])
        // ---------先序遍历的起点----------
        // 后序遍历的最后一个就是根节点
        val root = TreeNode(postOrder[postEnd])
        // 找到根节点在中序遍历中的位置
        val rootInOrder = inOrder.indexOf(root.`val`)
        // 位于rootInOrder左侧的就是新左子树的中序遍历起点
        val newInStartLeft = inStart
        val newInEndLeft = rootInOrder - 1
        // 位于rootInOrder左侧的就是新右子树的中序遍历起点
        val newInStartRight = rootInOrder + 1
        val newInEndRight = inEnd
        // 后序遍历的子节点长度与中序遍历的节点长度一致
        val newPostStartLeft = postStart
        val newPostEndLeft = newPostStartLeft + newInEndLeft - newInStartLeft
        // 前序遍历的子节点长度与中序遍历的节点长度一致
        val newPostStartRight = newPostEndLeft + 1
        val newPostEndRight = newPostStartRight + newInEndRight - newInStartRight

        // 做递归遍历
        // 左子树:
        root.left = buildTree(inOrder, postOrder, newInStartLeft, newInEndLeft, newPostStartLeft, newPostEndLeft)
        // 右子树:
        root.right = buildTree(inOrder, postOrder, newInStartRight, newInEndRight, newPostStartRight, newPostEndRight)

        // 返回根节点
        return root
    }

    @Test
    fun test() {
        val origin = TreeNode.inOrder(TreeNode.create(arrayOf(3, 9, 20, null, null, 15, 7))).apply { print("$this\n") }
        val result = TreeNode.inOrder(buildTree(intArrayOf(9, 3, 15, 20, 7), intArrayOf(9, 15, 7, 20, 3))).apply { print("$this\n") }
        assert(origin == result)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
