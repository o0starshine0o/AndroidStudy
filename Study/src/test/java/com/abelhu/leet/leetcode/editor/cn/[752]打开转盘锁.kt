import org.junit.Test

//你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
// 。每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。 
//
// 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。 
//
// 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。 
//
// 字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。 
//
// 
//
// 示例 1: 
//
// 
//输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
//输出：6
//解释：
//可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
//注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
//因为当拨动到 "0102" 时这个锁就会被锁定。
// 
//
// 示例 2: 
//
// 
//输入: deadends = ["8888"], target = "0009"
//输出：1
//解释：
//把最后一位反向旋转一次即可 "0000" -> "0009"。
// 
//
// 示例 3: 
//
// 
//输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], targ
//et = "8888"
//输出：-1
//解释：
//无法旋转到目标数字且不被锁定。
// 
//
// 示例 4: 
//
// 
//输入: deadends = ["0000"], target = "8888"
//输出：-1
// 
//
// 
//
// 提示： 
//
// 
// 死亡列表 deadends 的长度范围为 [1, 500]。 
// 目标数字 target 不会在 deadends 之中。 
// 每个 deadends 和 target 中的字符串的数字会在 10,000 个可能的情况 '0000' 到 '9999' 中产生。 
// 
// Related Topics 广度优先搜索 
// 👍 243 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution752 {
    fun openLock(
        deadEnds: Array<String>,
        target: String,
        toChecks: MutableSet<String> = mutableSetOf("0000"),
        checks: MutableSet<String> = mutableSetOf(),
        step: Int = 0
    ): Int {
        // 进行备份,防止在循环里增删
        val checkList = toChecks.toList()
        toChecks.clear()
        for (check in checkList) {
            // 找到了,直接返回
            if (check == target) return step
            // check不应该存在于deadEnds中,如果存在直接返回异常
            if (check in deadEnds) return -1
            // 没找到,把当前路径能做的选择都收集起来
            for (item in check.withIndex()) {
                // 做出选择
                val addChoose = String(check.toCharArray().apply { this[item.index] = if (item.value == '9') '0' else item.value + 1 })
                if (addChoose !in deadEnds && addChoose !in checks) toChecks.add(addChoose)
                // 做出另外一种选择
                val subChoose = String(check.toCharArray().apply { this[item.index] = if (item.value == '0') '9' else item.value - 1 })
                if (subChoose !in deadEnds && subChoose !in checks) toChecks.add(subChoose)
            }
            // 保存已经检查过的数据,防止环的出现
            checks.add(check)
        }
        // 如果找不到,就返回-1
        return if (toChecks.isEmpty()) -1 else openLock(deadEnds, target, toChecks, checks, step + 1)
    }

    @Test
    fun test0() {
        assert(6 == openLock(arrayOf("0201", "0101", "0102", "1212", "2002"), "0202"))
    }

    @Test
    fun test1() {
        assert(1 == openLock(arrayOf("8888"), "0009"))
    }

    @Test
    fun test2() {
        assert(-1 == openLock(arrayOf("8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"), "8888"))
    }

    @Test
    fun test3() {
        assert(-1 == openLock(arrayOf("0000"), "8888"))
    }
}
//leetcode submit region end(Prohibit modification and deletion)
