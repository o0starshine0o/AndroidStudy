import org.junit.Assert.assertEquals
import org.junit.Test

//假设有从 1 到 n 的 n 个整数。用这些整数构造一个数组 perm（下标从 1 开始），只要满足下述条件 之一 ，该数组就是一个 优美的排列 ：
//
// 
// perm[i] 能够被 i 整除 
// i 能够被 perm[i] 整除 
// 
//
// 给你一个整数 n ，返回可以构造的 优美排列 的 数量 。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 2
//输出：2
//解释：
//第 1 个优美的排列是 [1,2]：
//    - perm[1] = 1 能被 i = 1 整除
//    - perm[2] = 2 能被 i = 2 整除
//第 2 个优美的排列是 [2,1]:
//    - perm[1] = 2 能被 i = 1 整除
//    - i = 2 能被 perm[2] = 1 整除
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 15 
// 
//
// Related Topics 位运算 数组 动态规划 回溯 状态压缩 👍 355 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution526 {
    private fun countArrangement(n: Int): Int {
        return countLayer(n, 0, arrayListOf())
    }

    // 1, 这是排列问题, 要求所有数字不能重复
    // 2, exclude可以换成include, 减少has的判断
    // 3, 不用位操作就是回溯解法, 效率比较低, 不过也能通过测试
    private fun countLayer(limit: Int, layer: Int, exclude: ArrayList<Int>): Int {
        // base case
//        if (layer == limit) return 1.apply { println(exclude) }
        if (layer == limit) return 1
        // 记录每个节点可行解的数量: 所有子树求和
        var count = 0
        for (i in 1..limit) {
            if (i in exclude) continue
            // 注意!边界条件, 卡了很久
            val nextLayer = layer + 1
            if (i % nextLayer == 0 || nextLayer % i == 0) {
                // 做出选择
                exclude.add(i)
                // 进入下一层
                count += countLayer(limit, nextLayer, exclude)
                // 撤销选择
                exclude.remove(i)
            }
        }
        return count
    }

    @Test
    fun test0() {
        // 这种写法是可行的, 就是范围为空
        println(2 until 1)
    }

    @Test
    fun test1() {
        assertEquals(1, countArrangement(1))
    }

    @Test
    fun test2() {
        assertEquals(2, countArrangement(2))
    }

    @Test
    fun test3() {
        assertEquals(3, countArrangement(3))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
