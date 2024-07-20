import org.junit.Test
import kotlin.math.max

//给定一个 N 叉树，找到其最大深度。
//
// 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。 
//
// N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：root = [1,null,3,2,4,null,5,6]
//输出：3
// 
//
// 示例 2： 
//
// 
//
// 
//输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,
//null,13,null,null,14]
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 树的深度不会超过 1000 。 
// 树的节点数目位于 [0, 10⁴] 之间。 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 👍 388 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * 层序遍历, 需要把每层的节点保存起来
 */
class Solution559 {
    fun maxDepth(root: Node?): Int {
        // 终止条件
        if (root == null) return 0

        var maxChildrenDepth = 0
        for (node in root.children) {
            maxChildrenDepth = max(maxChildrenDepth, maxDepth(node))
        }
        // 最深子节点 + 根节点
        return maxChildrenDepth + 1
    }

    @Test
    fun test0() {
        assert(0 == maxDepth(null))
    }

    @Test
    fun test1() {
        val root = Node(0)
        assert(1 == maxDepth(root))
    }

    @Test
    fun test2() {
        val root = Node(0).apply { children = listOf(null) }
        assert(1 == maxDepth(root))
    }

    @Test
    fun test3() {
        val node5 = Node(5)
        val node6 = Node(6)
        val node3 = Node(3).apply { children = listOf(node5, node6) }
        val node2 = Node(2)
        val node4 = Node(4)
        val root = Node(1).apply { children = listOf(node3, node2, node4) }
        assert(3 == maxDepth(root))
    }

    @Test
    fun test4() {
        val node14 = Node(14)
        val node11 = Node(11).apply { children = listOf(node14) }
        val node12 = Node(12)
        val node13 = Node(13)
        val node6 = Node(6)
        val node7 = Node(7).apply { children = listOf(node11) }
        val node8 = Node(8).apply { children = listOf(node12) }
        val node9 = Node(9).apply { children = listOf(node13) }
        val node10 = Node(10)
        val node2 = Node(2)
        val node3 = Node(3).apply { children = listOf(node6, node7) }
        val node4 = Node(4).apply { children = listOf(node8) }
        val node5 = Node(5).apply { children = listOf(node9, node10) }
        val root = Node(1).apply { children = listOf(node2, node3, node4, node5) }
        assert(5 == maxDepth(root))
    }
}

class Node(var `val`: Int) {
    var children: List<Node?> = listOf()
}
//leetcode submit region end(Prohibit modification and deletion)
