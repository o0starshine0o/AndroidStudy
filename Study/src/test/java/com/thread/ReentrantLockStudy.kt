package com.thread

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
    val lock = ReentrantLock()
    val a = lock.newCondition()
    val b = lock.newCondition()
    val c = lock.newCondition()
    thread {
        lock.lock()
        listOf('a', 'b', 'c', 'd').forEach {
            a.await()
            print(it)
            b.signal()
        }
        c.signal()
        lock.unlock()
    }
    thread {
        lock.lock()
        listOf(1, 2, 3, 4).forEach {
            print(it)
            a.signal()
            b.await()
        }
        a.signal()
        lock.unlock()
    }
    thread {
        lock.lock()
        c.await()
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
        lock.unlock()
    }
}