package com.designpattern.structure.proxy.classproxy

class Adapter : RealObject(), IInterface {
    override fun function0() = println("Adapter")
}