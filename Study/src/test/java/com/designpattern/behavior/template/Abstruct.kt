package com.designpattern.behavior.template

abstract class Abstruct {
    var list = listOf(1, 4, 6, 9, 3)
    abstract fun sort(): List<Int>
    fun print() {
        list = sort()
        for (i in list) println(i)
    }
}