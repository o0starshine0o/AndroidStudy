import org.junit.Test

//亚历克斯和李用几堆石子在做游戏。偶数堆石子排成一行，每堆都有正整数颗石子 piles[i] 。
//
// 游戏以谁手中的石子最多来决出胜负。石子的总数是奇数，所以没有平局。 
//
// 亚历克斯和李轮流进行，亚历克斯先开始。 每回合，玩家从行的开始或结束处取走整堆石头。 这种情况一直持续到没有更多的石子堆为止，此时手中石子最多的玩家获胜。
// 
//
// 假设亚历克斯和李都发挥出最佳水平，当亚历克斯赢得比赛时返回 true ，当李赢得比赛时返回 false 。 
//
// 
//
// 示例： 
//
// 
//输入：[5,3,4,5]
//输出：true
//解释：
//亚历克斯先开始，只能拿前 5 颗或后 5 颗石子 。
//假设他取了前 5 颗，这一行就变成了 [3,4,5] 。
//如果李拿走前 3 颗，那么剩下的是 [4,5]，亚历克斯拿走后 5 颗赢得 10 分。
//如果李拿走后 5 颗，那么剩下的是 [3,4]，亚历克斯拿走后 4 颗赢得 9 分。
//这表明，取前 5 颗石子对亚历克斯来说是一个胜利的举动，所以我们返回 true 。
// 
//
// 
//
// 提示： 
//
// 
// 2 <= piles.length <= 500 
// piles.length 是偶数。 
// 1 <= piles[i] <= 500 
// sum(piles) 是奇数。 
// 
// Related Topics 极小化极大 数学 动态规划 
// 👍 287 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution877 {

    /**
     * 用回溯,否则无法处理跨越多级的情况,比如test3
     * 用回溯,算法的时间复杂度会达到2^n,对于数据规模比较大的情况,就会超时
     *
     * @param piles 石子
     * @param isAlex 是否是alex选择
     * @param start 开始的索引
     * @param end 结束的索引
     * @param alex alex目前拥有的石子
     * @param lee lee目前拥有的石子
     */
    fun stoneGame(piles: IntArray, isAlex: Boolean = true, start: Int = 0, end: Int = piles.size - 1, alex: Int = 0, lee: Int = 0): Boolean {
        // 没得选了,只剩下唯一一个,因为数组是偶数的,这时候一定是li进行拿取,结束递归
        if (start == end) return alex > lee + piles[start]
        // 二叉树回溯搜索
        return if (isAlex) {
            stoneGame(piles, !isAlex, start + 1, end, alex + piles[start], lee) || stoneGame(piles, !isAlex, start, end - 1, alex + piles[end], lee)
        } else {
            stoneGame(piles, !isAlex, start + 1, end, alex, lee + piles[start]) || stoneGame(piles, !isAlex, start, end - 1, alex, lee + piles[end])
        }
    }

    fun stoneGame1(piles: IntArray):Boolean {
        // 创建一个二维的dp,dp[i,j]代表从i到j当前玩家与另外一个玩家拥有石头的最大差值(也就意味着做出了最优解)
        // 所以i>j的情况无意义,i==j的时候,dp[i,j] = piles[i]
        // i<j时 dp[i,j] = max(piles[i] - dp[i+1, j], piles[j] - dp[i, j-1])
        val dp = Array(piles.size){ IntArray(piles.size){0} }
        // 首先进行初始化
        for (i in piles.indices) dp[i][i] = piles[i]
        // 注意遍历方向是从右下角开始,chong,一层一层遍历
        for (i in (0 until piles.size - 1).reversed()){
            for (j in i+1 until piles.size){
                dp[i][j] = kotlin.math.max(piles[i] - dp[i +1][j], piles[j] - dp[i][j-1])
            }
        }
        // 如果最后一个大于0,表明alex比lee的石头多
        return dp.last().last() > 0
    }

    @Test
    fun test0() {
        assert(stoneGame(intArrayOf(5, 3, 4, 5)))
    }

    @Test
    fun test1() {
        assert(stoneGame(intArrayOf(3, 7, 2, 3)))
    }

    @Test
    fun test2() {
        assert(stoneGame(intArrayOf(7, 8, 8, 10)))
    }

    @Test
    fun test3() {
        assert(stoneGame(intArrayOf(3, 2, 10, 4)))
    }

    @Test
    fun test10() {
        assert(stoneGame1(intArrayOf(5, 3, 4, 5)))
    }

    @Test
    fun test11() {
        assert(stoneGame1(intArrayOf(3, 7, 2, 3)))
    }

    @Test
    fun test12() {
        assert(stoneGame1(intArrayOf(7, 8, 8, 10)))
    }

    @Test
    fun test13() {
        assert(stoneGame1(intArrayOf(3, 2, 10, 4)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
