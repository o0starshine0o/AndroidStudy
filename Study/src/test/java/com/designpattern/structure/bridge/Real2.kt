package com.designpattern.structure.bridge

class Real2(bridge: IBridge) : Abstraction(bridge) {
    override fun draw() = bridge.draw()
}