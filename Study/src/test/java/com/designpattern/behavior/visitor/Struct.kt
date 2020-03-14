package com.designpattern.behavior.visitor

import kotlin.random.Random

class Struct {
    companion object {
        fun getData(): MutableList<IElement?> {
            val list = MutableList<IElement?>(0) { null }
            for (i in 0..9) {
                list.add(if (Random.nextInt() % 2 == 0) ElementA() else ElementB())
            }
            return list
        }
    }
}