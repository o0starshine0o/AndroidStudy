import com.abelhu.leet.utils.log
import org.junit.Assert
import org.junit.Test

//「以扣会友」线下活动所在场地由若干主题空间与走廊组成，场地的地图记作由一维字符串型数组 `grid`，字符串中仅包含 `"0"～"5"` 这 6 个字符。地
//图上每一个字符代表面积为 1 的区域，其中 `"0"` 表示走廊，其他字符表示主题空间。相同且连续（连续指上、下、左、右四个方向连接）的字符组成同一个主题空间。
//
//
//假如整个 `grid` 区域的外侧均为走廊。请问，不与走廊直接相邻的主题空间的最大面积是多少？如果不存在这样的空间请返回 `0`。
//
//**示例 1:**
//
//> 输入：`grid = ["110","231","221"]`
//>
//> 输出：`1`
//>
//> 解释：4 个主题空间中，只有 1 个不与走廊相邻，面积为 1。
//> ![image.png](https://pic.leetcode-cn.com/1613708145-rscctN-image.png)
//
//**示例 2:**
//
//> 输入：`grid = ["11111100000","21243101111","21224101221","11111101111"]`
//>
//> 输出：`3`
//>
//> 解释：8 个主题空间中，有 5 个不与走廊相邻，面积分别为 3、1、1、1、2，最大面积为 3。
//> ![image.png](https://pic.leetcode-cn.com/1613707985-KJyiXJ-image.png)
//
//**提示：**
//- `1 <= grid.length <= 500`
//- `1 <= grid[i].length <= 500`
//- `grid[i][j]` 仅可能是 `"0"～"5"`
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 14 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionLCS03 {
    companion object{
        private const val EMPTY = '0'
    }
    // 注意, 找不到返回0, 而不要返回Int.MIN_VALUE
    private var maxArea = 0

    private fun largestArea(grid: Array<String>): Int {
        // 思路:
        // 1, 层序遍历, 判断过的需要进行标记, 使用explore, 表示当前空间是否探索过
        // 2, 走廊跳过, 主题就往其他方向找相同的主题, DFS
        // 3, 返回面积和是否临近走廊
        val explore = Array(grid.size) { i -> Array<SubjectArea?>(grid[i].length) { null } }
        largestArea(grid, explore, 0, 0, Direction.Left)
        return maxArea
    }

    /**
     * 1, 判断是否需要探索的坐标超过了grid的限制, 如果超过了, 就返回('0', 0, true), 因为最外层为走廊
     * 2, 判断是否探索过了, 如果探索过了, 返回 explore[i][j]
     * 3, 进行标记explore, 也就是 explore[i][j] 赋值, 如果是走廊, 就直接返回了
     * 4, 探索其他3个方向, 返回值如果 subject 相同, 则相加计算总面积, 如果 subject 不同, 则不用相加, 计算是否临近走廊
     *    对于不同的subject, 如果不临近走廊, 则需要与maxArea比较大小
     *    如果nextSubjectArea的subject为Empty, 则subjectArea的isEmpty需要更新
     * 5, 计算当前区域的面积, 是否临近走廊
     *    如果不临近走廊, 如果不临近走廊, 则需要与maxArea比较大小
     *
     * @param grid 地图
     * @param explore 探索图
     * @param i 需要探索的横坐标
     * @param j 需要探索的纵坐标
     * @param direction 进入的方向(不需要探索)(注意: 是相对于这个元素来讲的)
     *
     * @return 当前区域的信息
     */
    private fun largestArea(
        grid: Array<String>,
        explore: Array<Array<SubjectArea?>>,
        i: Int,
        j: Int,
        direction: Direction
    ): SubjectArea {
        // 1, 判断是否需要探索的坐标超过了grid的限制, 如果超过了, 就返回('0', 0, true), 因为最外层为走廊
        if (i < 0 || i >= grid.size) return SubjectArea.Empty
        if (j < 0 || j >= grid[i].length) return SubjectArea.Empty
        // 2, 判断是否探索过了, 如果探索过了, 返回 explore[i][j]
        explore[i][j]?.apply { return this }
        // 3, 进行标记explore, 也就是 explore[i][j] 赋值
        val subject = grid[i][j]
        var isEmpty = subject == EMPTY
        // 注意! area此时要设置为0, 因为这个面积还没有定下来, 如果后续用到它, 会计算错误
        var area = 0
        val subjectArea = SubjectArea(subject, area, isEmpty)
        explore[i][j] = subjectArea.log { "explore: $i, $j" }
        if (isEmpty) return subjectArea.log { "explore[$i][$j]: $it" }
        // 4, 探索其他3个方向, 返回值如果 subject 相同, 则相加计算总面积, 如果 subject 不同, 则不用相加
        for (nextDirection in Direction.values()){
            // 避免循环
            if (nextDirection == direction) continue

            val nextSubjectArea = largestArea(grid, explore, i + nextDirection.i, j + nextDirection.j, nextDirection.opposite())
            if (nextSubjectArea.subject != subject) {
                // 4.1, 对于不同的subject, 如果不临近走廊, 则需要与maxArea比较大小
                if (!nextSubjectArea.isEmpty) maxArea = kotlin.math.max(nextSubjectArea.area, maxArea)
                // 注意: 4.2, 如果nextSubjectArea的subject为Empty, 则subjectArea的isEmpty需要更新
                if (nextSubjectArea.subject == EMPTY) isEmpty = isEmpty || nextSubjectArea.isEmpty
            }else{
                isEmpty = nextSubjectArea.isEmpty || isEmpty
                area += nextSubjectArea.area
            }
        }
        // 5, 计算当前区域的面积, 是否临近走廊
        subjectArea.isEmpty = isEmpty
        subjectArea.area = area + if (isEmpty) 0 else 1

        // 5.1, 如果不临近走廊, 如果不临近走廊, 则需要与maxArea比较大小
        if (!subjectArea.isEmpty) maxArea = kotlin.math.max(subjectArea.area, maxArea)

        return subjectArea.log { "explore[$i][$j]: $it" }
    }

    private data class SubjectArea(
        val subject: Char, // 本区域的主题
        var area: Int, // 此主题的探索面积
        var isEmpty: Boolean // 是否空白区域(走廊)
    ){
        companion object{
            // 走廊
            val Empty = SubjectArea('0', 0, true)
        }
    }

    private enum class Direction(val i: Int, val j: Int) {
        Left(0, -1),
        Up(-1, 0),
        Right(0, 1),
        Down(1, 0);

        // 注意! [0][0] 从右侧进入 [0][1], 那么对于[0][1]而言, 不需要探索的方向就是左侧
        fun  opposite(): Direction{
            return when(this){
                Left -> Right
                Up -> Down
                Right -> Left
                Down -> Up
            }
        }
    }

    @Test
    fun test0() {
        Assert.assertEquals(1, largestArea(arrayOf("110","231","221")))
    }

    @Test
    fun test1() {
        Assert.assertEquals(3, largestArea(arrayOf("11111100000","21243101111","21224101221","11111101111")))
    }

    @Test
    fun test2() {
        Assert.assertEquals(3, largestArea(arrayOf("331223024","342452255","434132314","314112223","255231220","100115032","440233544","455221424")))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
