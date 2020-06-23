package com.thread

import java.util.concurrent.CyclicBarrier
import kotlin.concurrent.thread

fun main() {
    val barrier = CyclicBarrier(2) {
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
    }
    var lock = true
    thread {
        listOf('a', 'b', 'c', 'd').forEach {
            while (lock) {
            }
            print(it)
            lock = true
        }
        barrier.await()
    }
    thread {
        listOf(1, 2, 3, 4).forEach {
            while (!lock) {
            }
            print(it)
            lock = false
        }
        barrier.await()
    }
}