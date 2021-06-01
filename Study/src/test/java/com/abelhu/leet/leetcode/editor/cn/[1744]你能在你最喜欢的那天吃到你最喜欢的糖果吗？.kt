import org.junit.Test

//给你一个下标从 0 开始的正整数数组 candiesCount ，其中 candiesCount[i] 表示你拥有的第 i 类糖果的数目。同时给你一个二维数
//组 queries ，其中 queries[i] = [favoriteTypei, favoriteDayi, dailyCapi] 。 
//
// 你按照如下规则进行一场游戏： 
//
// 
// 你从第 0 天开始吃糖果。 
// 你在吃完 所有 第 i - 1 类糖果之前，不能 吃任何一颗第 i 类糖果。 
// 在吃完所有糖果之前，你必须每天 至少 吃 一颗 糖果。 
// 
//
// 请你构建一个布尔型数组 answer ，满足 answer.length == queries.length 。answer[i] 为 true 的条件是
//：在每天吃 不超过 dailyCapi 颗糖果的前提下，你可以在第 favoriteDayi 天吃到第 favoriteTypei 类糖果；否则 answer[
//i] 为 false 。注意，只要满足上面 3 条规则中的第二条规则，你就可以在同一天吃不同类型的糖果。 
//
// 请你返回得到的数组 answer 。 
//
// 
//
// 示例 1： 
//
// 
//输入：candiesCount = [7,4,5,3,8], queries = [[0,2,2],[4,2,4],[2,13,1000000000]]
//输出：[true,false,true]
//提示：
//1- 在第 0 天吃 2 颗糖果(类型 0），第 1 天吃 2 颗糖果（类型 0），第 2 天你可以吃到类型 0 的糖果。
//2- 每天你最多吃 4 颗糖果。即使第 0 天吃 4 颗糖果（类型 0），第 1 天吃 4 颗糖果（类型 0 和类型 1），你也没办法在第 2 天吃到类型 
//4 的糖果。换言之，你没法在每天吃 4 颗糖果的限制下在第 2 天吃到第 4 类糖果。
//3- 如果你每天吃 1 颗糖果，你可以在第 13 天吃到类型 2 的糖果。
// 
//
// 示例 2： 
//
// 
//输入：candiesCount = [5,2,6,4,1], queries = [[3,1,2],[4,10,3],[3,10,100],[4,100,3
//0],[1,3,1]]
//输出：[false,true,true,false,false]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= candiesCount.length <= 105 
// 1 <= candiesCount[i] <= 105 
// 1 <= queries.length <= 105 
// queries[i].length == 3 
// 0 <= favoriteTypei < candiesCount.length 
// 0 <= favoriteDayi <= 109 
// 1 <= dailyCapi <= 109 
// 
// Related Topics 数学 
// 👍 30 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1744 {
    /**
     * 每天至少吃一个,最多吃dailyCap[i]颗
     * 所以要在第favoriteDay[i]天能吃到favoriteType[i]类型的糖果,需要满足:
     * i*1 <= sum(candiesCount[i]) <= sum(candiesCount[i+1]) <= i*dailyCap[i]
     *
     * queries[i] = [favoriteTypei, favoriteDayi, dailyCapi]
     */
    fun canEat(candiesCount: IntArray, queries: Array<IntArray>): BooleanArray {
        // 前缀和
        val total = LongArray(candiesCount.size)
        for (i in candiesCount.indices) total[i] = if (i == 0) candiesCount[i].toLong() else total[i - 1] + candiesCount[i]
        // 结果
        val result = BooleanArray(queries.size)
        for ((index, query) in queries.withIndex()) {
            // 如果每天吃1颗,能吃多少颗(需要包含第0天也吃一个)
            val min = (1 + query[1]) * 1L
            // 如果每天吃dailyCap[i]颗,能吃多少颗
            val max = (1 + query[1]) * query[2].toLong()
            // 能否吃到喜欢的糖果,只要满足 min <= candyMin <= candyMax <= max
            result[index] = !(min > total[query[0]] || max <= if (query[0] - 1 < 0) 0 else total[query[0] - 1])
        }
        return result
    }

    @Test
    fun test0() {
        assert(
            booleanArrayOf(true, false, true).contentEquals(
                canEat(
                    intArrayOf(7, 4, 5, 3, 8),
                    arrayOf(intArrayOf(0, 2, 2), intArrayOf(4, 2, 4), intArrayOf(2, 13, 1000000000))
                )
            )
        )
    }

    @Test
    fun test1() {
        assert(
            booleanArrayOf(false, true, true, false, false).contentEquals(
                canEat(
                    intArrayOf(5, 2, 6, 4, 1),
                    arrayOf(intArrayOf(3, 1, 2), intArrayOf(4, 10, 3), intArrayOf(3, 10, 100), intArrayOf(4, 100, 30), intArrayOf(1, 3, 1))
                )
            )
        )
    }

    @Test
    fun test2() {
        assert(
            booleanArrayOf(false, false, false).contentEquals(
                canEat(
                    intArrayOf(7, 11, 5, 3, 8),
                    arrayOf(intArrayOf(2, 2, 6), intArrayOf(4, 2, 4), intArrayOf(2, 13, 1000000000))
                )
            )
        )
    }

    @Test
    fun test3() {
        assert(
            booleanArrayOf(true).contentEquals(
                canEat(
                    intArrayOf(
                        5215,
                        14414,
                        67303,
                        93431,
                        44959,
                        34974,
                        22935,
                        64205,
                        28863,
                        3436,
                        45640,
                        34940,
                        38519,
                        5705,
                        14594,
                        30510,
                        4418,
                        87954,
                        8423,
                        65872,
                        79062,
                        83736,
                        47851,
                        64523,
                        15639,
                        19173,
                        88996,
                        97578,
                        1106,
                        17767,
                        63298,
                        8620,
                        67281,
                        76666,
                        50386,
                        97303,
                        26476,
                        95239,
                        21967,
                        31606,
                        3943,
                        33752,
                        29634,
                        35981,
                        42216,
                        88584,
                        2774,
                        3839,
                        81067,
                        59193,
                        225,
                        8289,
                        9295,
                        9268,
                        4762,
                        2276,
                        7641,
                        3542,
                        3415,
                        1372,
                        5538,
                        878,
                        5051,
                        7631,
                        1394,
                        5372,
                        2384,
                        2050,
                        6766,
                        3616,
                        7181,
                        7605,
                        3718,
                        8498,
                        7065,
                        1369,
                        1967,
                        2781,
                        7598,
                        6562,
                        7150,
                        8132,
                        1276,
                        6656,
                        1868,
                        8584,
                        9442,
                        8762,
                        6210,
                        6963,
                        4068,
                        1605,
                        2780,
                        556,
                        6825,
                        4961,
                        4041,
                        4923,
                        8660,
                        4114
                    ),
                    arrayOf(intArrayOf(91, 244597, 840227137))
                )
            )
        )
    }
}
//leetcode submit region end(Prohibit modification and deletion)
