package com.designpattern.structure.composite

class Component : Composite {
    val children = MutableList<Composite>(0) { Component() }
    override fun name() = println(Component::class.java.simpleName)
}