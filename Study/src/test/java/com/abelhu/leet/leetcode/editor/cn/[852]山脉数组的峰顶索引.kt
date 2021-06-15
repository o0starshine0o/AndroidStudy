import org.junit.Test

//符合下列属性的数组 arr 称为 山脉数组 ：
// 
// arr.length >= 3 
// 存在 i（0 < i < arr.length - 1）使得：
// 
// arr[0] < arr[1] < ... arr[i-1] < arr[i] 
// arr[i] > arr[i+1] > ... > arr[arr.length - 1] 
// 
// 
// 
//
// 给你由整数组成的山脉数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 
//1] > ... > arr[arr.length - 1] 的下标 i 。 
//
// 
//
// 示例 1： 
//
// 
//输入：arr = [0,1,0]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：arr = [0,2,1,0]
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：arr = [0,10,5,2]
//输出：1
// 
//
// 示例 4： 
//
// 
//输入：arr = [3,4,5,1]
//输出：2
// 
//
// 示例 5： 
//
// 
//输入：arr = [24,69,100,99,79,78,67,36,26,19]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 3 <= arr.length <= 104 
// 0 <= arr[i] <= 106 
// 题目数据保证 arr 是一个山脉数组 
// 
//
// 
//
// 进阶：很容易想到时间复杂度 O(n) 的解决方案，你可以设计一个 O(log(n)) 的解决方案吗？ 
// Related Topics 二分查找 
// 👍 185 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution852 {
    fun peakIndexInMountainArray(arr: IntArray): Int {
        var start = 0
        var end = arr.size - 1
        while (start < end) {
            val middle = start + (end - start) / 2
            when {
                arr[middle - 1] > arr[middle] -> end = middle
                arr[middle + 1] > arr[middle] -> start = middle + 1
                else -> {
                    start = middle
                    end = middle
                }
            }
        }
        return start
    }

    @Test
    fun test0() {
        assert(1 == peakIndexInMountainArray(intArrayOf(0, 1, 0)))
    }

    @Test
    fun test1() {
        assert(1 == peakIndexInMountainArray(intArrayOf(0, 2, 1, 0)))
    }

    @Test
    fun test2() {
        assert(1 == peakIndexInMountainArray(intArrayOf(0, 10, 5, 2)))
    }

    @Test
    fun test3() {
        assert(2 == peakIndexInMountainArray(intArrayOf(3, 4, 5, 1)))
    }

    @Test
    fun test4() {
        assert(2 == peakIndexInMountainArray(intArrayOf(24, 69, 100, 99, 79, 78, 67, 36, 26, 19)))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
