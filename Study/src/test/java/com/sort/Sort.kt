package com.sort

import org.junit.Test

class Sort {

    /**
     * 冒泡排序，n*n
     * 两个数比较大小，较大的数下沉，较小的数冒起来
     */
    @Test
    fun bubbleSort() {
        val list = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("bubbleSort:${list}")
        for ((i, iNum) in list.withIndex()) {
            for ((j, jNum) in list.withIndex()) {
                if (i >= j) continue
                if (iNum < jNum) swap(list, i, j)
            }
        }
        println("----${list}----")
    }

    /**
     * 选择排序，n*n
     * 找到最小的数值与第一个元素交换
     */
    @Test
    fun selectionSort() {
        val list = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("selectionSort:${list}")
        for ((i, iNum) in list.withIndex()) {
            var max = iNum
            var index = i
            for ((j, jNum) in list.withIndex()) {
                if (i >= j) continue
                if (jNum > max) {
                    max = jNum
                    index = j
                }
            }
            swap(list, i, index)
        }
        println("----${list}----")
    }

    private fun swap(list: ArrayList<Int>, i: Int, j: Int) {
        // 注意，相同的数做异或为0,会导致交换失败
        if (i == j || list[i] == list[j]) return
        list[i] = list[i] xor list[j]
        list[j] = list[i] xor list[j]
        list[i] = list[i] xor list[j]
    }
}