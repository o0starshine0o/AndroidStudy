import com.abelhu.base.TreeNode
import org.junit.Test

//给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点 node 的新值等于原树中大于或等于
// node.val 的值之和。 
//
// 提醒一下，二叉搜索树满足下列约束条件： 
//
// 
// 节点的左子树仅包含键 小于 节点键的节点。 
// 节点的右子树仅包含键 大于 节点键的节点。 
// 左右子树也必须是二叉搜索树。 
// 
//
// 注意：本题和 1038: https://leetcode-cn.com/problems/binary-search-tree-to-greater-s
//um-tree/ 相同 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
//输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
// 
//
// 示例 2： 
//
// 输入：root = [0,null,1]
//输出：[1,null,1]
// 
//
// 示例 3： 
//
// 输入：root = [1,0,2]
//输出：[3,3,2]
// 
//
// 示例 4： 
//
// 输入：root = [3,2,4,1]
//输出：[7,9,4,10]
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数介于 0 和 104 之间。 
// 每个节点的值介于 -104 和 104 之间。 
// 树中的所有值 互不相同 。 
// 给定的树为二叉搜索树。 
// 
// Related Topics 树 深度优先搜索 二叉搜索树 递归 
// 👍 490 👎 0


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
class Solution538 {
    private var sum = 0

    fun convertBST(root: TreeNode?): TreeNode? {
        if (root == null) return null
        //// 注意,这里的中序遍历是先遍历了右子树,而不是通常情况的左子树
        convertBST(root.right)
        root.`val` += sum
        sum = root.`val`
        convertBST(root.left)
        return root
    }

    @Test
    fun test() {
        val origin = TreeNode.create(arrayOf(4, 1, 6, 0, 2, 5, 7, null, null, null, 3, null, null, null, 8))
        val result = TreeNode.create(arrayOf(30, 36, 21, 36, 35, 26, 15, null, null, null, 33, null, null, null, 8))
        assert(TreeNode.inOrder(result).apply { print("$this\n") } == TreeNode.inOrder(convertBST(origin)).apply { print("$this\n") })
    }

    @Test
    fun test1() {
        val origin = TreeNode.create(arrayOf(0, null, 1))
        val result = TreeNode.create(arrayOf(1, null, 1))
        assert(TreeNode.inOrder(result).apply { print("$this\n") } == TreeNode.inOrder(convertBST(origin)).apply { print("$this\n") })
    }

    @Test
    fun test2() {
        val origin = TreeNode.create(arrayOf(1, 0, 2))
        val result = TreeNode.create(arrayOf(3, 3, 2))
        assert(TreeNode.inOrder(result).apply { print("$this\n") } == TreeNode.inOrder(convertBST(origin)).apply { print("$this\n") })
    }

    @Test
    fun test3() {
        val origin = TreeNode.create(arrayOf(3, 2, 4, 1))
        val result = TreeNode.create(arrayOf(7, 9, 4, 10))
        assert(TreeNode.inOrder(result).apply { print("$this\n") } == TreeNode.inOrder(convertBST(origin)).apply { print("$this\n") })
    }

}
//leetcode submit region end(Prohibit modification and deletion)
