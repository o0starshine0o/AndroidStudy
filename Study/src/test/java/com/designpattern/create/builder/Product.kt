package com.designpattern.create.builder

class Product {
    var name: String? = null
    var type: String? = null
    // and so on
    fun show() = "$type:$name"
}