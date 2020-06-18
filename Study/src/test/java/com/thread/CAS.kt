package com.thread

import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

fun main() {
    var lock = true
    val countDownLatch = CountDownLatch(2)
    thread {
        listOf('a', 'b', 'c', 'd').forEach {
            while (lock) {
            }
            print(it)
            lock = true
        }
        countDownLatch.countDown()
    }
    thread {
        listOf(1, 2, 3, 4).forEach {
            while (!lock) {
            }
            print(it)
            lock = false
        }
        countDownLatch.countDown()
    }
    countDownLatch.await()
    thread {
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
    }
}