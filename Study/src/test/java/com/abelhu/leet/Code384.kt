package com.abelhu.leet

import org.junit.Test

class Code384 {

    class Solution(private var nums: IntArray) {

        private val temp: IntArray = nums.clone()

        /** Resets the array to its original configuration and return it. */
        fun reset(): IntArray {
            nums = temp.clone()
            return nums
        }

        /**
         * 随机一个元素，交换这个元素和末尾的元素
         **/
        fun shuffle(): IntArray {
            for (i in nums.size - 1 downTo 1) {
                val random = kotlin.random.Random.nextInt(i)
                val temp = nums[i]
                nums[i] = nums[random]
                nums[random] = temp
            }
            return nums
        }
    }

    @Test
    fun test0() {
        val code = Solution(IntArray(3) { i -> i })
        code.reset()
        code.shuffle()
    }

}