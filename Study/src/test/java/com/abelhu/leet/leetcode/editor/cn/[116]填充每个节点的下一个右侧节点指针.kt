import com.abelhu.base.TreeNode
import org.junit.Test

//给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
//
// 
//struct Node {
//  int val;
//  Node *left;
//  Node *right;
//  Node *next;
//} 
//
// 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。 
//
// 初始状态下，所有 next 指针都被设置为 NULL。 
//
// 
//
// 进阶： 
//
// 
// 你只能使用常量级额外空间。 
// 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。 
// 
//
// 
//
// 示例： 
//
// 
//
// 
//输入：root = [1,2,3,4,5,6,7]
//输出：[1,#,2,3,#,4,5,6,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 
//next 指针连接，'#' 标志着每一层的结束。
// 
//
// 
//
// 提示： 
//
// 
// 树中节点的数量少于 4096 
// -1000 <= node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 
// 👍 400 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a Node.
 * class Node(var `val`: Int) {
 *     var left: Node? = null
 *     var right: Node? = null
 *     var next: Node? = null
 * }
 */

class Solution116 {
    /**
     * 节点要么在本层就能找到next
     * 要么通过祖父节点就能找到next
     */
    fun connect(root: TreeNode?): TreeNode? {
        // 注意,这里递归的不是一个节点,而是把2个节点放在一起递归
        connect2(root?.left, root?.right)
        // 返回结果
        return root
    }

    private fun connect2(left: TreeNode?, right: TreeNode?) {
        // 退出条件
        if (left == null || right == null) return
        // 前序
        left.next = right
        // 额外的一个合并过程
        connect2(left.right, right.left)
        // 左子节点
        connect2(left.left, left.right)
        // 右子节点
        connect2(right.left, right.right)
    }

    @Test
    fun test0() {
        val root = TreeNode(0).apply {
            left = TreeNode(1).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(7)
                    right = TreeNode(8)
                }
                right = TreeNode(4).apply {
                    left = TreeNode(9)
                    right = TreeNode(10)
                }
            }
            right = TreeNode(2).apply {
                left = TreeNode(5).apply {
                    left = TreeNode(11)
                    right = TreeNode(12)
                }
                right = TreeNode(6).apply {
                    left = TreeNode(13)
                    right = TreeNode(14)
                }
            }
        }
        connect(root)
        print(root)
    }
}
//leetcode submit region end(Prohibit modification and deletion)
