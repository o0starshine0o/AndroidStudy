package com.designpattern.behavior.observer

class Observer(private val name: String) : IObserver {
    override fun update() {
        println("Observer update: $name")
    }
}