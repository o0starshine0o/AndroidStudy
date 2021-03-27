import com.abelhu.base.TreeNode
import org.junit.Test

//给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的
//根节点的引用。 
//
// 一般来说，删除节点可分为两个步骤： 
//
// 
// 首先找到需要删除的节点； 
// 如果找到了，删除它。 
// 
//
// 说明： 要求算法时间复杂度为 O(h)，h 为树的高度。 
//
// 示例: 
//
// 
//root = [5,3,6,2,4,null,7]
//key = 3
//
//    5
//   / \
//  3   6
// / \   \
//2   4   7
//
//给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
//
//一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。
//
//    5
//   / \
//  4   6
// /     \
//2       7
//
//另一个正确答案是 [5,2,6,null,4,null,7]。
//
//    5
//   / \
//  2   6
//   \   \
//    4   7
// 
// Related Topics 树 
// 👍 416 👎 0


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
class Solution450 {
    fun deleteNode(root: TreeNode?, key: Int): TreeNode? {
        if (root == null) return null
        when {
            // ---- 前序遍历 ---- //
            root.`val` == key -> {
                // 第一种情况,要删除的节点木有子节点
                if (root.left == null && root.right == null) return null
                // 第二种情况,要删除的节点只有一个子节点
                if (root.right == null) return root.left
                if (root.left == null) return root.right

                // 第三种情况, 要删除的节点有2个子节点
                val max = getNodeMax(root.left)
                root.`val` = max!!.`val`
                root.left = deleteNode(root.left, root.`val`)
                return root
            }
            root.`val` < key -> root.right = deleteNode(root.right, key)
            root.`val` > key -> root.left = deleteNode(root.left, key)
        }
        return root
    }

    private fun getNodeMax(root: TreeNode?): TreeNode? {
        if (root == null) return null
        if (root.right == null) return root
        return getNodeMax(root.right)
    }

    @Test
    fun test0() {
        val root = TreeNode.create(arrayOf(5, 3, 6, 2, 4, null, 7))
        val result = TreeNode.levelOrder(deleteNode(root, 3))
        assert("5, 4, 6, 2, null, null, 7" == result || "5, 2, 6, null, 4, null, 7" == result)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
