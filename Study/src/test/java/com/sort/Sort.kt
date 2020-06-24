package com.sort

import org.junit.Test

class Sort {

    /**
     * 冒泡排序，n*n
     * 两个数比较大小，较大的数下沉，较小的数冒起来
     */
    @Test
    fun bubbleSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("bubbleSort:${list}")
        for (i in list.indices)
            for (j in i + 1 until list.size)
                if (list[i] < list[j]) swap(list, i, j)
        println("----${list}----")
    }

    /**
     * 选择排序，n*n
     * 找到最大的数值与第一个元素交换
     */
    @Test
    fun selectionSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
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

    /**
     * 插入排序，n*n
     * 最好情况，n
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的
     * 每次是和尾部的数据进行交换CAS
     */
    @Test
    fun insertionSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("insertionSort:${list}")
        for (i in list.indices) {
            for (j in i downTo 1) {
                if (list[j - 1] < list[j]) swap(list, j - 1, j) else break
            }
        }
        println("----${list}----")
    }

    /**x
     * 希尔排序，n**3/2
     * 根据某一增量分为若干子序列，并对子序列分别进行插入排序
     */
    @Test
    fun shellSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("insertionSort:${list}")
        var gap = list.size / 2
        // 每次减少gap的数值，到0意味着排序结束
        while (gap > 0) {
            // k代表着分为多少组
            for (k in 0 until gap) {
                // i和之前的插入排序一样，代表i之前的数据已经排列好
                for (i in k until list.size step gap) {
                    // j和之前的插入排序一样，代表需要最终把目标数值插入到什么位置
                    for (j in i downTo k + 1 step gap) {
                        if (list[j - gap] < list[j]) swap(list, j - gap, j) else break
                    }
                }
            }
            gap /= 2
        }
        println("----${list}----")
    }

    /**
     * 快排，log(n)
     * 分治
     */
    @Test
    fun quickSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("quickSort:${list}")
        quickSortStub(list, 0, list.size - 1)
        println("----${list}----")
    }

    private fun quickSortStub(list: ArrayList<Int>, start: Int, end: Int) {
        if (start >= end) return
        var left = start
        var right = end
        val main = list[left]
        while (left < right) {
            while (left < right && list[right] < main) right--
            if (left >= right) break
            list[left] = list[right]
            left++
            while (left < right && list[left] > main) left++
            if (left >= right) break
            list[right] = list[left]
            right--
        }
        list[left] = main
        quickSortStub(list, start, left - 1)
        quickSortStub(list, left + 1, end)
    }

    /**
     * 归并排序，log(n)
     */
    @Test
    fun mergeSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("mergeSort:${list}")
        mergeSortStub(list, 0, list.size - 1)
        println("----${list}----")
    }

    private fun mergeSortStub(list: ArrayList<Int>, start: Int, end: Int) {
        if (start >= end) return
        val middle = (start + end) / 2
        mergeSortStub(list, start, middle)
        mergeSortStub(list, middle + 1, end)
        mergeSortMerge(list, start, middle + 1, end)
    }

    private fun mergeSortMerge(list: ArrayList<Int>, start: Int, middle: Int, end: Int) {
        var i = start
        var j = middle
        // 注意kotlin使用的是浅拷贝，这里需要深拷贝
        val tempList = list.subList(start, end + 1).toIntArray().copyOf()
        for (k in start..end) {
            when {
                // 注意这里面的边界条件判断，i如果大于middle了，表明前一个数组已经拷贝完成，后面的数组应该直接怼上去
                i >= middle -> list[k] = tempList[j - start].apply { j++ }
                // j的判断条件就不是middle了，而是end，而且不用加等号
                j > end -> list[k] = tempList[i - start].apply { i++ }
                else -> list[k] = if (tempList[i - start] > tempList[j - start]) tempList[i - start].apply { i++ } else tempList[j - start].apply { j++ }
            }
        }
    }

    private fun swap(list: ArrayList<Int>, i: Int, j: Int) {
        // 注意，相同的数做异或为0,会导致交换失败
        if (i == j || list[i] == list[j]) return
        list[i] = list[i] xor list[j]
        list[j] = list[i] xor list[j]
        list[i] = list[i] xor list[j]
    }
}