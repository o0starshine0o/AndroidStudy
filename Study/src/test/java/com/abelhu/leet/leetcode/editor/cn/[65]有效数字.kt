import org.junit.Test

//有效数字（按顺序）可以分成以下几个部分：
//
// 
// 一个 小数 或者 整数 
// （可选）一个 'e' 或 'E' ，后面跟着一个 整数 
// 
//
// 小数（按顺序）可以分成以下几个部分： 
//
// 
// （可选）一个符号字符（'+' 或 '-'） 
// 下述格式之一：
// 
// 至少一位数字，后面跟着一个点 '.' 
// 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字 
// 一个点 '.' ，后面跟着至少一位数字 
// 
// 
// 
//
// 整数（按顺序）可以分成以下几个部分： 
//
// 
// （可选）一个符号字符（'+' 或 '-'） 
// 至少一位数字 
// 
//
// 部分有效数字列举如下： 
//
// 
// ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1",
// "53.5e93", "-123.456e789"] 
// 
//
// 部分无效数字列举如下： 
//
// 
// ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"] 
// 
//
// 给你一个字符串 s ，如果 s 是一个 有效数字 ，请返回 true 。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "0"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：s = "e"
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：s = "."
//输出：false
// 
//
// 示例 4： 
//
// 
//输入：s = ".1"
//输出：true
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 20 
// s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，或者点 '.' 。 
// 
// Related Topics 数学 字符串 
// 👍 216 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution65 {


    fun isNumber(s: String): Boolean {
        // 初始化状态机到init状态
        var state:State = Init.instance
        // 循环判断c,转移到下一状态
        for (c in s) state = state.next(c) ?: return false
        // 位于接受状态是才认为是有效数字
        return state.canAccept()
    }

    abstract class State() {
        // 使用hashMap来保证O(1)的时间查找
        val map = HashMap<Type, State>()
        // 是否是可接受状态,默认为false,如果是可接受状态,需要override
        open fun canAccept() = false
        // 状态转移方程
        fun next(char: Char): State? = map[getType(char)]

        private fun getType(char: Char) = when (char) {
            'e', 'E' -> Type.E
            '+', '-' -> Type.Symbol
            '.' -> Type.Dot
            in '0'..'9' -> Type.Num
            else -> Type.Error
        }
    }

    /**
     * 0, 初始状态
     */
    class Init private constructor(): State() {
        companion object {
            val instance by lazy { Init() }
        }
        init {
            map[Type.Num] = Integer.instance
            map[Type.Symbol] = Symbol.instance
            map[Type.Dot] = LeftDotWithOutInt.instance
        }
    }

    /**
     * 1, 符号位
     */
    class Symbol private constructor() : State() {
        companion object {
            val instance by lazy { Symbol() }
        }
        init {
            map[Type.Num] = Integer.instance
            map[Type.Dot] = LeftDotWithOutInt.instance
        }
    }

    /**
     * 2, 整数部分
     */
    class Integer private constructor(): State() {
        companion object {
            val instance by lazy { Integer() }
        }
        init {
            map[Type.Num] = this
            map[Type.E] = E.instance
            map[Type.Dot] = LeftDotWithInt.instance
        }
        override fun canAccept() = true
    }

    /**
     * 3, 小数点,左有整数
     */
    class LeftDotWithInt private constructor(): State() {
        companion object {
            val instance by lazy { LeftDotWithInt() }
        }
        init {
            map[Type.Num] = Float.instance
            map[Type.E] = E.instance
        }
        override fun canAccept() = true
    }

    /**
     * 4, 小数部分
     */
    class Float:State(){
        companion object {
            val instance by lazy { Float() }
        }
        init {
            map[Type.Num] = this
            map[Type.E] = E.instance
        }
        override fun canAccept() = true
    }

    /**
     * 5, 字符E
     */
    class E private constructor():State(){
        companion object {
            val instance by lazy { E() }
        }
        init {
            map[Type.Num] = ExpNum.instance
            map[Type.Symbol] = Exp.instance
        }
    }

    /**
     * 6, 指数符号
     */
    class Exp private constructor(): State(){
        companion object {
            val instance by lazy { Exp() }
        }
        init {
            map[Type.Num] = ExpNum.instance
        }
    }

    /**
     * 7, 指数数字
     */
    class ExpNum private constructor():State(){
        companion object {
            val instance by lazy { ExpNum() }
        }
        init {
            map[Type.Num] = this
        }
        override fun canAccept() = true
    }

    /**
     * 8, 小数点,左无整数
     */
    class LeftDotWithOutInt private constructor(): State() {
        companion object {
            val instance by lazy { LeftDotWithOutInt() }
        }
        init {
            map[Type.Num] = Float.instance
        }
    }

    enum class Type {
        E, Num, Symbol, Dot, Error
    }

    @Test
    fun test0(){
        listOf("2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789").forEach { assert(isNumber(it).apply { println("$it $this") }) }
    }

    @Test
    fun test1(){
        listOf("abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53").forEach { assert(!isNumber(it)) }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
