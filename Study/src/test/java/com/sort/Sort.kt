package com.sort

import org.junit.Test
import java.util.*
import kotlin.math.pow

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
     * 快排，n*log(n)
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
     * 归并排序，n*log(n)
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

    /**
     * 堆排序，n*log(n)
     * 最大堆的性质：每个节点的值都大于等于其子节点的值
     * 最小堆的性质：每个节点的值都小于等于其子节点的值
     * 对于节点i，左节点为2i+1，右节点为2i+2
     * 算法：循环交换顶点和最后一个元素，然后通过旋转维持堆的性质
     */
    @Test
    fun heapSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).apply { shuffle() }
        println("heapSort:${list}")
        // 构建最小堆，从最后一个非叶节点开始调整，每次减少1，最后到达根节点，表明已经构建完成
        for (i in list.size / 2 - 1 downTo 0) heapSortStub(list, i, list.size)
        println("heapSortBuild:${list}")
        // 交换堆顶元素和末尾元素，重新调整堆的大小，并且重新建立堆
        for (i in list.size - 1 downTo 0) {
            swap(list, 0, i)
            heapSortStub(list, 0, i)
        }
        println("----${list}----")
    }

    /**
     * 调整堆，默认top以外的顺序不管，只调整以top为堆顶的堆
     */
    private fun heapSortStub(list: ArrayList<Int>, top: Int, end: Int) {
        // 从左子节点开始
        var t = top
        var k = t * 2 + 1
        while (k < end) {
            // 如果左子节点的值大于右子节点，把k指向右节点，以获取较小的值
            if (k + 1 < end && list[k] > list[k + 1]) k++
            // 如果子节点的值小于父节点，交换2者的值，否则父节点已经是最小值，表示调整完成，直接结束调整
            if (list[k] < list[t]) swap(list, k, t) else break
            // 调整t为新的根节点，调整k的值为新的左子节点
            t = k
            k = 2 * k + 1
        }
    }

    /**
     * 基数排序
     * 先按照个位数字，放到桶里面，再取出
     * 然后按照十位数字，放到桶里面，再取出
     */
    @Test
    fun radixSort() {
        val list = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 34, 56, 89, 91, 233).apply { shuffle() }
        println("heapSort:${list}")
        val bucket = Array(10) { LinkedList<Int>() }
        // 获取最高位，表示要桶排序几次
        var max = Int.MIN_VALUE
        list.forEach { max = maxOf(it.toString().length, max) }
        // 按照位数进行桶排
        for (t in 0 until max) {
            // 把所有数字全放到桶里
            for (num in list) bucket[(num / 10.0.pow(t) % 10).toInt()].add(num)
            // 把桶里数字全部取出来，放到原始数组中
            var i = 0
            bucket.forEach { temp -> temp.forEach { list[i++] = it } }
            // 清空桶
            bucket.forEach { it.clear() }
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