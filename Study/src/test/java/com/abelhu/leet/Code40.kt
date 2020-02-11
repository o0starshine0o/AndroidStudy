package com.abelhu.leet

import org.junit.Test

class Code40 {

    fun combinationSum2(candidates: IntArray, target: Int, start: Int = 0, sorted: Boolean = false): MutableList<MutableList<Int>> {
        val result = MutableList(0) { MutableList(0) { 0 } }
        if (target == 0) result.add(MutableList(0) { 0 })
        // 如果是默认数据，先进行一次排序
        if (!sorted) {
            candidates.sort()
            candidates.reverse()
        }
        var current = Int.MIN_VALUE
        for ((i, num) in candidates.withIndex()) {
            // 排除不需要的数组元素
            if (i < start) continue
            // 排除重复项
            if (num == current) continue
            else current = num
            // 如果遍历值大于目标值，停止搜索
            if (num > target) continue
            // 如果小于等于目标值，递归查找<需排除当前的数字和之前的数字>
            val temp = combinationSum2(candidates, target - num, i + 1, true)
            // 对每一个列表项，添加当前数据
            for (list in temp) {
                list.add(num)
                // 添加到最终结果
                result.add(list)
            }
        }
        return result
    }

    @Test
    fun test0() = println(combinationSum2(intArrayOf(10, 1, 2, 7, 6, 1, 5), 8))

    @Test
    fun test1() = println(combinationSum2(intArrayOf(2, 5, 2, 1, 2), 5))

    @Test
    fun test2() = println(combinationSum2(intArrayOf(2), 1))

}