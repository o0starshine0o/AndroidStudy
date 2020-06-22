package com.designpattern.structure.adapter.objectadapter

class Adapter : IInterface {
    private val real = RealObject()
    override fun function0() = println("Adapter")
    override fun function1() = real.function1()
}