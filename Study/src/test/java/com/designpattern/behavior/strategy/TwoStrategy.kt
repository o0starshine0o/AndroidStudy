package com.designpattern.behavior.strategy

class TwoStrategy : IStrategy {
    override fun strategy() {
        println(TwoStrategy::class.java.simpleName)
    }
}