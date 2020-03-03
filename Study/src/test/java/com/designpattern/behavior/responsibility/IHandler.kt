package com.designpattern.behavior.responsibility

abstract class IHandler {
    abstract fun handlerRequest()
    var handler: IHandler? = null
}