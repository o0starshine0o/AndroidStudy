import org.junit.Test

//给定一个整数，写一个函数来判断它是否是 4 的幂次方。如果是，返回 true ；否则，返回 false 。 
//
// 整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4x 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 16
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：n = 5
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：n = 1
//输出：true
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
// 进阶： 
//
// 
// 你能不使用循环或者递归来完成本题吗？ 
// 
// Related Topics 位运算 
// 👍 202 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution342 {
    /**
     * 2的冥次在二进制中只有一个1,比如:
     * 1 == 0x0000_0001
     * 2 == 0x0000_0010
     * 4 == 0x0000_0100
     * 8 == 0x0000_1000
     * 16 == 0x0001_0000
     * 32 == 0x0010_0000
     * 64 == 0x0100_0000
     * 128 == 0x1000_0000
     * 所以只要判断二进制表示中是否只有一个1就完成了
     */
    fun isPowerOfFour(n: Int): Boolean {
        if (n <= 0) return false
        // 记录n的二进制中1的数量
        var count = 0
        // int是4字节,32位,所以要移动31次,进行判断
        for (i in 0..31) {
            val current = n shr i and 1
            // 只有奇数位(移动偶数)的时候才计算
            if (i % 2 == 0) count += current
            // 但凡偶数位出现1,直接返回false
            else if (current > 0) return false
            // 所有位中最多出现一次1
            if (count > 1) return false
        }
        return count == 1
    }

    /**
     * 最优解参考231题
     * 可以使用mask来获取所有奇数位0x_0101_0101 = 5
     * 也可以用0x_1010_1010 = a 来判断
     */

    @Test
    fun test0() {
        assert(isPowerOfFour(1))
        assert(isPowerOfFour(4))
        assert(isPowerOfFour(256))

        assert(!isPowerOfFour(2))
        assert(!isPowerOfFour(3))
        assert(!isPowerOfFour(-1))
        assert(!isPowerOfFour(7))
        assert(!isPowerOfFour(8))
        assert(!isPowerOfFour(9))
        assert(!isPowerOfFour(-2147483648))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
