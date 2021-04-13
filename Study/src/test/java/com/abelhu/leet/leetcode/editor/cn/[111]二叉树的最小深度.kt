import com.abelhu.base.TreeNode
import org.junit.Test

//给定一个二叉树，找出其最小深度。
//
// 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。 
//
// 说明：叶子节点是指没有子节点的节点。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：root = [2,null,3,null,4,null,5,null,6]
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数的范围在 [0, 105] 内 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 
// 👍 487 👎 0


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
class Solution111 {
    fun minDepth(root: TreeNode?): Int {
        val nodes = mutableListOf(root)
        var depth = 0
        while (nodes.filterNotNull().isNotEmpty()) {
            depth++
            for (node in nodes.toList()) {
                if (node == null) continue
                if (node.left == null && node.right == null) return depth
                // 准备下层数据
                nodes.remove(node)
                nodes.add(node.left)
                nodes.add(node.right)
            }
        }
        return depth
    }

    @Test
    fun test0() {
        assert(2 == minDepth(TreeNode.create(arrayOf(3, 9, 20, null, null, 15, 7))))
    }

    @Test
    fun test1() {
        assert(
            5 == minDepth(
                TreeNode.create(
                    arrayOf(
                        2,
                        null,
                        3,
                        null,
                        null,
                        null,
                        4,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        5,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        6
                    )
                )
            )
        )
    }
}
//leetcode submit region end(Prohibit modification and deletion)
