import com.abelhu.base.TreeNode
import org.junit.Test

//在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“
//房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。 
//
// 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。 
//
// 示例 1: 
//
// 输入: [3,2,3,null,3,null,1]
//
//     3
//    / \
//   2   3
//    \   \ 
//     3   1
//
//输出: 7 
//解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7. 
//
// 示例 2: 
//
// 输入: [3,4,5,1,3,null,1]
//
//     3
//    / \
//   4   5
//  / \   \ 
// 1   3   1
//
//输出: 9
//解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
// 
// Related Topics 树 深度优先搜索 动态规划 
// 👍 831 👎 0


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
class Solution337 {
    // 使用备忘录来记录抢劫当前节点能获取到的收益
    val map = HashMap<TreeNode, Int>()
    fun rob(root: TreeNode?): Int {
        if (root == null) return 0
        // 使用备忘录来消除子问题
        map[root]?.apply { return this }
        // 动归问题,先建立状态转移方程
        // 对于一个节点而言,最大的收益分2种情况
        // 1, 抢劫当前节点+不抢劫当前当前节点的直接子节点收益
        // 2, 不抢劫当前节点+抢劫2个直接子节点的收益
        val current = root.`val` + rob(root.left?.left) + rob(root.left?.right) + rob(root.right?.left) + rob(root.right?.right)
        val children = rob(root.left) + rob(root.right)
        // 做出选择,并且记录
        return kotlin.math.max(current, children).apply { map[root] = this }
    }

    @Test
    fun test0() {
        assert(7 == rob(TreeNode.create(arrayOf(3, 2, 3, null, 3, null, 1))))
    }

    @Test
    fun test1() {
        assert(9 == rob(TreeNode.create(arrayOf(3, 4, 5, 1, 3, null, 1))))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
