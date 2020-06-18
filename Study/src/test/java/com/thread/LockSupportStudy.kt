package com.thread

import java.util.concurrent.locks.LockSupport
import kotlin.concurrent.thread


fun main() {
    var a: Thread? = null
    val c = thread { LockSupport.park();listOf('A', 'B', 'C', 'D').forEach { print(it) } }
    val b = thread {
        listOf('a', 'b', 'c', 'd').forEach {
            LockSupport.park()
            print(it)
            LockSupport.unpark(a)
        }
        LockSupport.unpark(c)
    }
    a = thread {
        listOf(1, 2, 3, 4).forEach {
            print(it)
            LockSupport.unpark(b)
            LockSupport.park()
        }
    }
}