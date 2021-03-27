import com.abelhu.base.TreeNode
import org.junit.Test

//给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
//
// 例如， 
//
// 
//给定二叉搜索树:
//
//        4
//       / \
//      2   7
//     / \
//    1   3
//
//和值: 2
// 
//
// 你应该返回如下子树: 
//
// 
//      2     
//     / \   
//    1   3
// 
//
// 在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NULL。 
// Related Topics 树 
// 👍 117 👎 0


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
class Solution700 {
    fun searchBST(root: TreeNode?, `val`: Int): TreeNode? {
        if (root == null) return null
        if (root.`val` == `val`) return root
        if (root.`val` > `val`) return searchBST(root.left, `val`)
        if (root.`val` < `val`) return searchBST(root.right, `val`)
        return null
    }

    @Test
    fun test0() {
        val root = TreeNode.create(arrayOf(4, 2, 7, 1, 3, null, null))
        assert(root?.left == searchBST(root, 2))
    }

    @Test
    fun test1() {
        val root = TreeNode.create(arrayOf(4, 2, 7, 1, 3, null, null))
        assert(null == searchBST(root, 5))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
