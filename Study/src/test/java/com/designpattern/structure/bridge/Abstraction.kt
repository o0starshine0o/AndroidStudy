package com.designpattern.structure.bridge

abstract class Abstraction constructor(val bridge: IBridge) {
    abstract fun draw()
}