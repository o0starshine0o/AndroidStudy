package com.designpattern.structure.proxy

class RealObject : Binder {
    override fun function() = println("RealObject")
}