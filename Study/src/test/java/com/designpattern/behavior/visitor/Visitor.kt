package com.designpattern.behavior.visitor

class Visitor : IVisitor {
    override fun visit(element: IElement) {
        element.doSomething()
    }
}