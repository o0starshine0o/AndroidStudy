package com.designpattern.structure.adapter.interfaceadapter

interface ProxyInterface : IInterface {
    override fun b() {
        println("proxy fun b()")
    }

    override fun c() {
        println("proxy fun c()")
    }
}