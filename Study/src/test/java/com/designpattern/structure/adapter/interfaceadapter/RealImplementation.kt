package com.designpattern.structure.adapter.interfaceadapter

class RealImplementation : ProxyInterface {
    override fun a() {
        println("real interface implementation")
    }
}