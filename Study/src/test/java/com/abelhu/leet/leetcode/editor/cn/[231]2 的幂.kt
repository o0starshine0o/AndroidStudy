import org.junit.Test

//给你一个整数 n，请你判断该整数是否是 2 的幂次方。如果是，返回 true ；否则，返回 false 。
//
// 如果存在一个整数 x 使得 n == 2x ，则认为 n 是 2 的幂次方。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 1
//输出：true
//解释：20 = 1
// 
//
// 示例 2： 
//
// 
//输入：n = 16
//输出：true
//解释：24 = 16
// 
//
// 示例 3： 
//
// 
//输入：n = 3
//输出：false
// 
//
// 示例 4： 
//
// 
//输入：n = 4
//输出：true
// 
//
// 示例 5： 
//
// 
//输入：n = 5
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// -231 <= n <= 231 - 1 
// 
//
// 
//
// 进阶：你能够不使用循环/递归解决此问题吗？ 
// Related Topics 位运算 数学 
// 👍 356 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution231 {
    /**
     * 2的冥次在二进制中只有一个1,比如:
     * 1 == 0x0000_0001
     * 2 == 0x0000_0010
     * 4 == 0x0000_0100
     * 8 == 0x0000_1000
     * 所以只要判断二进制表示中是否只有一个1就完成了
     */
    fun isPowerOfTwo(n: Int): Boolean {
        if (n <= 0) return false
        // 记录n的二进制中1的数量
        var count = 0
        // int是4字节,32位,所以要移动31次,进行判断
        for (i in 0..31) {
            count += n shr i and 1
            if (count > 1) return false
        }
        return count == 1
    }

    @Test
    fun test0() {
        assert(isPowerOfTwo(1))
        assert(isPowerOfTwo(2))
        assert(isPowerOfTwo(4))
        assert(isPowerOfTwo(8))

        assert(!isPowerOfTwo(3))
        assert(!isPowerOfTwo(-1))
        assert(!isPowerOfTwo(7))
        assert(!isPowerOfTwo(9))
        assert(!isPowerOfTwo(-2147483648))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
