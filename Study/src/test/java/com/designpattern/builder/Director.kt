package com.designpattern.builder

class Director {
    private val builder = ConcreteBuilder()
    fun create0() = builder.setName("Name0").setType("Type0").build()
    fun create1() = builder.setName("Name1").setType("Type1").build()
}