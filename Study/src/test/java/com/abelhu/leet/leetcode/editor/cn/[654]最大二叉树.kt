import com.abelhu.base.TreeNode
import org.junit.Test

//给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
//
// 
// 二叉树的根是数组 nums 中的最大元素。 
// 左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。 
// 右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。 
// 
//
// 返回有给定数组 nums 构建的 最大二叉树 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [3,2,1,6,0,5]
//输出：[6,3,5,null,2,0,null,null,1]
//解释：递归调用如下所示：
//- [3,2,1,6,0,5] 中的最大值是 6 ，左边部分是 [3,2,1] ，右边部分是 [0,5] 。
//    - [3,2,1] 中的最大值是 3 ，左边部分是 [] ，右边部分是 [2,1] 。
//        - 空数组，无子节点。
//        - [2,1] 中的最大值是 2 ，左边部分是 [] ，右边部分是 [1] 。
//            - 空数组，无子节点。
//            - 只有一个元素，所以子节点是一个值为 1 的节点。
//    - [0,5] 中的最大值是 5 ，左边部分是 [0] ，右边部分是 [] 。
//        - 只有一个元素，所以子节点是一个值为 0 的节点。
//        - 空数组，无子节点。
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,1]
//输出：[3,null,2,null,1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 1000 
// 0 <= nums[i] <= 1000 
// nums 中的所有整数 互不相同 
// 
// Related Topics 树 
// 👍 255 👎 0


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
class Solution654 {
    private fun constructMaximumBinaryTree(nums: IntArray): TreeNode? {
        // 结束条件
        if (nums.isEmpty()) return null
        //  ----先序遍历----
        // 找到数组中最大的那个值,用它来构造根节点
        var index = 0
        var max = nums[index]
        for ((i, value) in nums.withIndex()) if (value > max) {
            index = i
            max = value
        }
        // 返回当前节点
        return TreeNode(max).apply {
            // 递归调用左子树
            left = constructMaximumBinaryTree(nums.sliceArray(0 until index))
            // 递归调用右子树
            right = constructMaximumBinaryTree(nums.sliceArray(index + 1 until nums.size))
        }
    }

    @Test
    fun test() {
        val result = listOf(6, 3, 5, null, 2, 0, null, null, null, null, 1, null, null, null, null).map { item -> item.toString() }
        assert(result == TreeNode.levelOrderList(constructMaximumBinaryTree(intArrayOf(3, 2, 1, 6, 0, 5))).apply { print(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
