package com.reflection

import org.junit.Test

class Construction(private val base: Base) {
    fun test() {
        val clazz = this::class.java
        val base = MyBase("Construction")
        val constructor = clazz.getConstructor(MyBase::class.java)
        val instance = constructor.newInstance(base)
        instance.toPrint()
        toPrint()
    }

    private fun toPrint() {
        println(base.name)
    }
}

class MyBase(name: String) : Base(name)

open class Base(val name: String)

class Test {

    @Test
    fun test() {
        val construction = Construction(MyBase("good luck"))
        construction.test()
    }
}