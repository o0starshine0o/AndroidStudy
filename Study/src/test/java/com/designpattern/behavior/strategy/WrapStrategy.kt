package com.designpattern.behavior.strategy

class WrapStrategy(private val strategy: IStrategy) {
    fun run() {
        strategy.strategy()
    }
}