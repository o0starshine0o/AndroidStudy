package com.designpattern.structure.facade

class Facede {
    private val system0 = SubSystem0()
    private val system1 = SubSystem1()
    fun run() {
        system0.function0()
        system1.function1()
    }
}