package com.designpattern.instance

class DoubleCheckKt private constructor() {
    companion object {
        val instance: DoubleCheckKt by lazy { DoubleCheckKt() }
    }
}