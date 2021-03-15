import com.abelhu.base.TreeNode
import org.junit.Test

//给定一个二叉树，判断其是否是一个有效的二叉搜索树。
//
// 假设一个二叉搜索树具有如下特征： 
//
// 
// 节点的左子树只包含小于当前节点的数。 
// 节点的右子树只包含大于当前节点的数。 
// 所有左子树和右子树自身必须也是二叉搜索树。 
// 
//
// 示例 1: 
//
// 输入:
//    2
//   / \
//  1   3
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//    5
//   / \
//  1   4
//     / \
//    3   6
//输出: false
//解释: 输入为: [5,1,4,null,null,3,6]。
//     根节点的值为 5 ，但是其右子节点值为 4 。
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 959 👎 0


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
class Solution98 {
    fun isValidBST(root: TreeNode?, min: Int? = null, max: Int? = null): Boolean {
        // 结束条件
        if (root == null) return true
        //// 先序遍历起点
        root.left?.apply { if (`val` >= root.`val`) return false }
        root.right?.apply { if (`val` <= root.`val`) return false }
        max?.apply { if (root.`val` >= this) return false }
        min?.apply { if (root.`val` <= this) return false }
        // 左右子树
        val left = isValidBST(root.left, min, kotlin.math.min(max ?: root.`val`, root.`val`))
        val right = isValidBST(root.right, kotlin.math.max(min ?: root.`val`, root.`val`), max)
        // 输出结果
        return left && right
    }

    @Test
    fun test() = assert(isValidBST(TreeNode.create(arrayOf(2, 1, 3))))

    @Test
    fun test1() = assert(!isValidBST(TreeNode.create(arrayOf(5, 1, 4, null, null, 3, 6))))

    @Test
    fun test2() = assert(!isValidBST(TreeNode.create(arrayOf(1, 1, null))))

    @Test
    fun test3() = assert(!isValidBST(TreeNode.create(arrayOf(5, 4, 6, null, null, 3, 7))))

    @Test
    fun test4() = assert(!isValidBST(TreeNode.create(arrayOf(32, 26, 47, 19, null, null, 56, null, 27))))

    @Test
    fun test5() = assert(isValidBST(TreeNode.create(arrayOf(2147483647))))

    @Test
    fun test6() = assert(!isValidBST(TreeNode.create(arrayOf(3, 1, 5, 0, 2, 4, 6, null, null, null, 3))))
}
//leetcode submit region end(Prohibit modification and deletion)
