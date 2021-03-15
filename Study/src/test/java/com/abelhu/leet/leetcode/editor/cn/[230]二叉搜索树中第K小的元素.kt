import com.abelhu.base.TreeNode
import org.junit.Test
import java.util.*

//给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,1,4,null,2], k = 1
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：root = [5,3,6,2,4,null,null,1], k = 3
//输出：3
// 
//
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数为 n 。 
// 1 <= k <= n <= 104 
// 0 <= Node.val <= 104 
// 
//
// 
//
// 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？ 
// Related Topics 树 二分查找 
// 👍 364 👎 0


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
class Solution {
    private val nodeList = ArrayList<TreeNode>()
    private fun kthSmallest(root: TreeNode?, k: Int): Int {
        traverse(root)
        return if (nodeList.size >= k) nodeList[k - 1].`val` else 0
    }

    private fun traverse(root: TreeNode?) {
        if (root == null) return
        traverse(root.left)
        nodeList.add(root)
        traverse(root.right)
    }

    @Test
    fun test() {
        assert(1 == kthSmallest(TreeNode.create(arrayOf(3, 1, 4, null, 2)), 1).apply { print("$this\n") })
    }

    @Test
    fun test1() {
        assert(3 == kthSmallest(TreeNode.create(arrayOf(3, 1, 4, null, 2)), 3).apply { print("$this\n") })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
