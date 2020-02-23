package com.abelhu.generic

import org.junit.Test

/**
 * 声明处协变
 * `out` 修饰符称为型变注解， 由于出现在参数声明处，称之为`声明处型变`
 * `T`被`out`修饰，只能出现在输出位置
 * 回报是`Source<Any>`可以作为`Source<String>`的超类
 */
interface Source<out T> {
    fun next(): T
}

/**
 * 逆变
 * 只可以被消费而不可以被生产
 */
interface Use<in T> {
    fun create(t: T): Int
}

/**
 * 具有确定的上界: 所有Number的子类都可以
 * 可以有多个上界，使用where子句
 */
interface Upper<T : Number>

interface Upper2<T> where T : Number, T : Comparable<T>

interface Star<in T, out U>

/**
 * `Array`的类型依然是`Array<T>`，但是被限制为了`<out Any>`
 * 这里是使用处的型变，称之为类型投影
 * `out` 限定了上界：参数只能是`Number`的子类，比如`Int`等
 * `in`限定了下界：参数只能是`Number`的父类，比如`Any`，因为任何类都是`Any`的子类
 */
fun copy(from: Array<out Number>, to: Array<in Number>) {
    assert(from.size <= to.size)
    for (i in from.indices) to[i] = from[i]
}

fun test(source: Source<String>) {
    // `o`可以安全的接收`source`是因为`Source<out T>`中的`T`被`out`修饰，只能用作生产者，向外提供输出
    val o: Source<Any> = source
    o.next()
}

fun test2(use: Use<Number>) {
    // 这个不需要型变就可以了
    use.create(1.toFloat())
    // `use`可以被赋值给d是因为`Use`有型变
    val d: Use<Double> = use
}

/**
 * 星投影
 * 等同于<in Nothing, out Any?>
 */
fun test3(star: Star<*, *>) {}

/**
 * 函数泛型
 */
fun <T> test4(item: T): T = item

fun <T : GenericKt> T.test(): Boolean = true
inline fun <reified T> T.tag(): String = T::class.java.simpleName

open class GenericKt {
    @Test
    fun test3() {
        val from = arrayOf(0, 1, 2)
        val to = Array<Any>(3) { 1 }
        // 结合注释理解型变的上下界确定
        copy(from, to)
    }

    @Test
    fun test4() = assert(GenericKt().test())
}
