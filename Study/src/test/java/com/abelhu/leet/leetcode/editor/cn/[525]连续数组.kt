import org.junit.Test

//给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [0,1]
//输出: 2
//说明: [0, 1] 是具有相同数量0和1的最长连续子数组。 
//
// 示例 2: 
//
// 
//输入: nums = [0,1,0]
//输出: 2
//说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// nums[i] 不是 0 就是 1 
// 
// Related Topics 哈希表 
// 👍 310 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution525 {
    /**
     * 前缀和
     * hashMap:存放current,index
     * 原理:
     * 前n个数的和为S,经过m个数字后,和如果还是S,那么就能证明[n+1,m]这个区间的数字和为0,也就是说含有相同数量的0,1
     */
    fun findMaxLength(nums: IntArray): Int {
        // 前缀和
        var total = 0
        // map主要就是保存total第一次出现的地方,方便后面出现相同的数值的时候做减法测出距离
        val map  = HashMap<Int, Int>()
        // 返回值
        var max = 0
        // 插入一个辅助的值,避免出错
        map[0] = -1

        for ((index, num) in nums.withIndex()){
            total += if (num == 0) -1 else num
            // 如果map的key含有total,表明之前的计算的值到目前index所在位置之间的数之和为0,这时候就要更新max了
            if (map.containsKey(total)) max = kotlin.math.max(max, index - (map[total] ?: 0))
            // 保存前缀和第一次出现的位置,方便之后比较index之差
            else map[total] = index
        }

        return max
    }

    fun findMaxLength2(nums: IntArray): Int {
        // 先进行一次转换,把所有的0替换成-1,方便接下来运算
        for ((index, num) in nums.withIndex()) nums[index] = if (num == 0) -1 else 1
        // 使用dp保存计算结果:从i到j的子字串和
        val dp = IntArray(nums.size) { Int.MIN_VALUE }
        // 结果
        var result = 0
        // 计算前缀和
        for ((index, num) in nums.withIndex()) dp[index] = if (index == 0) num else dp[index - 1] + num
        // 双循环查找最大值
        for (i in nums.indices) for (j in i until nums.size) if (dp[j] - dp[i] + nums[i] == 0) result = kotlin.math.max(result, j - i + 1)
        // 返回结果
        return result
    }

    @Test
    fun test0() {
        assert(2 == findMaxLength(intArrayOf(0, 1)).apply { println(this) })
    }

    @Test
    fun test1() {
        assert(2 == findMaxLength(intArrayOf(0, 1, 0)).apply { println(this) })
    }
}
//leetcode submit region end(Prohibit modification and deletion)
