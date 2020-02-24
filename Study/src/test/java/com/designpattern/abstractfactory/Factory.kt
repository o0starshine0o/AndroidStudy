package com.designpattern.abstractfactory

class Factory : IFactory {
    override fun create0() = Product0()

    override fun create1() = Product1()
}