package com.designpattern.structure.bridge

class Real(bridge: IBridge) : Abstraction(bridge) {
    override fun draw() = bridge.draw()
}