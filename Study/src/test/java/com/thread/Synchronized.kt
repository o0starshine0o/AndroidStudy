package com.thread

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.thread

@InternalCoroutinesApi
fun main() {
    val lock = Object()
    var start = true
    thread {
        val a = thread {
            synchronized(lock) {
                while (start) {
                    lock.notify()
                    lock.wait()
                }
                listOf('a', 'b', 'c', 'd').forEach {
                    print(it)
                    lock.notify()
                    lock.wait()
                }
                lock.notify()
            }
        }
        val b = thread {
            synchronized(lock) {
                start = false
                listOf(1, 2, 3, 4).forEach {
                    print(it)
                    lock.notify()
                    lock.wait()
                }
                lock.notify()
            }
        }
        for (i in listOf(a, b)) i.join()
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
    }
}