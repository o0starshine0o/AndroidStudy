package com.designpattern.structure.decorator

class Decorator(private val component: IComponent) : IDecorator(component) {

    override fun function() {
        println("Decorator")
        component.function()
    }
}