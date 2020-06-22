package com.designpattern.structure.adapter.classadapter

class Adapter : RealObject(), IInterface {
    override fun function0() = println("Adapter")
}