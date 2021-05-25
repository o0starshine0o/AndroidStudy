package com.thread

import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 * 需要准确输出
 * 1a2b3c4dABCD
 */
fun main() {
    val num = 0
    val char = 1
    val countDownLatch = CountDownLatch(2)
    val lock = ReentrantLock()
    val charLock = lock.newCondition()
    val numLock = lock.newCondition()
    var now = num
    thread {
        listOf('a', 'b', 'c', 'd').forEach {
            try {
                lock.lock()
                while (now == num) charLock.await()
                print(it)
                numLock.signalAll()
                if ('d' != it) charLock.await()
            } finally {
                lock.unlock()
            }
        }
        countDownLatch.countDown()
    }
    thread {
        listOf(1, 2, 3, 4).forEach {
            try {
                lock.lock()
                print(it)
                now = char
                charLock.signalAll()
                if (4 != it) numLock.await()
            } finally {
                lock.unlock()
            }
        }
        countDownLatch.countDown()
    }
    countDownLatch.await()
    thread {
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
    }
}