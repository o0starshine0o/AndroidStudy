package com.designpattern.behavior.state

class ConcreteStateB : State {
    override fun handle(context: Context) {
        println(ConcreteStateB::class.java.simpleName)
    }
}