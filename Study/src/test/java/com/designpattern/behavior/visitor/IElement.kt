package com.designpattern.behavior.visitor

abstract class IElement {
    abstract fun doSomething()
    fun accept(visitor: IVisitor? = null) {
        visitor?.visit(this)
    }
}