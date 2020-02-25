package com.designpattern.create.builder

class ConcreteBuilder : Builder {
    private val product = Product()
    override fun setName(name: String?): Builder {
        product.name = name
        return this
    }

    override fun setType(type: String?): Builder {
        product.type = type
        return this
    }

    override fun build() = product
}