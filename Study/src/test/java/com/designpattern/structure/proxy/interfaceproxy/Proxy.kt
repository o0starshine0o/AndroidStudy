package com.designpattern.structure.proxy.interfaceproxy

class Proxy : Binder {
    private val real = RealObject()
    override fun function() {
        preFunction()
        real.function()
        afterFunction()
    }

    private fun preFunction() = println("preFunction")
    private fun afterFunction() = println("afterFunction")
}