package com.designpattern.behavior.strategy

class OneStrategy : IStrategy {
    override fun strategy() {
        println(OneStrategy::class.java.simpleName)
    }
}