import com.abelhu.base.TreeNode
import org.junit.Test

//给你二叉树的根结点 root ，请你将它展开为一个单链表：
//
// 
// 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。 
// 展开后的单链表应该与二叉树 先序遍历 顺序相同。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,5,3,4,null,6]
//输出：[1,null,2,null,3,null,4,null,5,null,6]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 树中结点数在范围 [0, 2000] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？ 
// Related Topics 树 深度优先搜索 
// 👍 731 👎 0


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
class Solution114 {
    private fun flatten(root: TreeNode?) {
        if (root == null) return
        // 左子树拉平
        flatten(root.left)
        // 右子树拉平
        flatten(root.right)

        /**** 后序遍历位置 ****/

        // 这里有一步操作:如果左子树为空,就不需要在移动了,直接返回即可
        if (root.left == null) return
        // 先把右子树移到左子树上去
        var leftTemp = root.left
        while (leftTemp?.right != null) leftTemp = leftTemp.right
        leftTemp?.right = root.right
        // 再把左子树移到右子树上去
        root.right = root.left
        // 设置左子树为空
        root.left = null
    }

    @Test
    fun testPre() {
        val tree = TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))
        flatten(tree)
        assert("1,2,3,4,5,6," == TreeNode.preOrder(tree).apply { print("$this\n") })
    }

    @Test
    fun testIn() {
        val tree = TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))
        flatten(tree)
        assert("1,2,3,4,5,6," == TreeNode.inOrder(tree).apply { print("$this\n") })
    }

    @Test
    fun testPost() {
        val tree = TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))
        flatten(tree)
        assert("6,5,4,3,2,1," == TreeNode.postOrder(tree).apply { print("$this\n") })
    }

    @Test
    fun testLevel() {
        val tree = TreeNode.create(arrayOf(1, 2, 5, 3, 4, null, 6))
        flatten(tree)
        val result = """
            1, 
            null, 2, 
            null, null, null, 3, 
            null, null, null, null, null, null, null, 4, 
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5, 
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6
        """.trimIndent().replace("\n", "")
        assert(result == TreeNode.levelOrder(tree).apply { print("$this\n") })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
