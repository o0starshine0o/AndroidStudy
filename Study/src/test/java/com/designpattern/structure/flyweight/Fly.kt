package com.designpattern.structure.flyweight

class Fly constructor(val name: String) : IFly {
    override fun fly() = "$name:${Fly::class.java.simpleName}"
}