package com.designpattern.proto

import org.junit.Test

class Test {
    @Test
    fun test() {
        val max = 10
        var proto = Proto()
        var start = System.currentTimeMillis()
        for (i in 0..max) proto = Proto()
        val newTime = System.currentTimeMillis() - start
        start = System.currentTimeMillis()
        for (i in 0..max) proto = proto.clone()
        val cloneTime = System.currentTimeMillis() - start
        println("clone time:$cloneTime, new time:$newTime")
        assert(newTime > cloneTime) { println("clone time:$cloneTime, new time:$newTime") }
    }
}