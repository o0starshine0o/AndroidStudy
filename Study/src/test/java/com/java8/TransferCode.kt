package com.java8

import org.junit.Test

class TransferCode {
    companion object {
        fun isGreen(apple: Apple) = apple.color == 1
        fun isHeavy(apple: Apple) = apple.weight > 50
    }

    data class Apple(var color: Int, var weight: Int)

    interface Predicate<in T, out O> {
        fun test(t: T): O
    }

    private fun filterApples(inventory: List<Apple>, predicate: Predicate<Apple, Boolean>): List<Apple> {
        val apples = MutableList(0) { i -> Apple(1, i * 10) }
        inventory.forEach { apple -> apple.takeIf { predicate.test(it) }?.apply { apples.add(this) } }
        return apples
    }

    @Test
    fun testApple() {
        // 貌似kotlin不支持这样的写法,如果以后想明白了,回过头再改改
//        filterApples(MutableList(10) { i -> Apple(1, i * 10) }, Companion::isGreen)
    }
}