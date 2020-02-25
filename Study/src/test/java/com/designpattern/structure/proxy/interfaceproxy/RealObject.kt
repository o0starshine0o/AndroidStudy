package com.designpattern.structure.proxy.interfaceproxy

class RealObject : Binder {
    override fun function() = println("RealObject")
}