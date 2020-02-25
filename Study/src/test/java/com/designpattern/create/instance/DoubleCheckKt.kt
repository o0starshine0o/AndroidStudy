package com.designpattern.create.instance

class DoubleCheckKt private constructor() {
    companion object {
        val instance: DoubleCheckKt by lazy { DoubleCheckKt() }
    }
}