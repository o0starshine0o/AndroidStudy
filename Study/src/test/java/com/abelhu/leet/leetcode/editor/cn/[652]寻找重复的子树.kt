import com.abelhu.base.TreeNode
import org.junit.Test
import java.util.*
import kotlin.collections.HashMap

//给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
//
// 两棵树重复是指它们具有相同的结构以及相同的结点值。 
//
// 示例 1： 
//
//         1
//       / \
//      2   3
//     /   / \
//    4   2   4
//       /
//      4
// 
//
// 下面是两个重复的子树： 
//
//       2
//     /
//    4
// 
//
// 和 
//
//     4
// 
//
// 因此，你需要以列表的形式返回上述重复子树的根结点。 
// Related Topics 树 
// 👍 243 👎 0


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
class Solution652 {
    private val nodeMap = HashMap<String, Int>()
    val result = LinkedList<TreeNode>()

    fun findDuplicateSubtrees(root: TreeNode?): List<TreeNode?> {
        traverse(root)
        return result
    }

    private fun traverse(root: TreeNode?): String {
        // 终止条件
        if (root == null) return "#"
        //// 后续遍历的起点 ////
        val left = traverse(root.left)
        val right = traverse(root.right)
        // 节点的签名(序列化),这里可以用hash来优化存储空间
        val signature = "$left,$right,${root.`val`}".hashCode().toString()
        // map中加1
        nodeMap[signature] = 1 + nodeMap.getOrDefault(signature, 0)
        // 只有第二个才加入到list中
        if (nodeMap[signature] == 2) result.add(root)
        // 返回签名
        return signature
    }

    @Test
    fun test() {
        val treeNode = TreeNode.create(arrayOf(1, 2, 3, 4, null, 2, 4, null, null, null, null, 4, null, null, null))
        assert("4,2" == findDuplicateSubtrees(treeNode).map { node -> node?.`val`.toString() }.reduce { acc, s -> "$acc,$s" }.apply { print(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
