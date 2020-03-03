package com.designpattern.behavior.responsibility

class ConcreteHandler2 : IHandler() {
    override fun handlerRequest() {
        println(ConcreteHandler2::class.java.simpleName)
        handler?.handlerRequest()
    }
}