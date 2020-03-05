package com.designpattern.behavior.state

class ConcreteStateA : State {
    override fun handle(context: Context) {
        println(ConcreteStateA::class.java.simpleName)
    }
}