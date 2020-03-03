package com.designpattern.behavior.responsibility

class ConcreteHandler : IHandler() {
    override fun handlerRequest() {
        println(ConcreteHandler::class.java.simpleName)
        handler?.handlerRequest()
    }
}