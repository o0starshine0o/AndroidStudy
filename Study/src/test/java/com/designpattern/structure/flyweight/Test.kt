package com.designpattern.structure.flyweight

import org.junit.Test
import kotlin.random.Random

class Test {
    @Test
    fun test() {
        val factory = Factory()
        for (i in 0..500) {
            val fly = factory.getFly(Random.nextInt('a'.toInt(), 'g'.toInt()).toChar().toString())
            println("${fly.hashCode()}:${fly.fly()}")
        }
    }
}