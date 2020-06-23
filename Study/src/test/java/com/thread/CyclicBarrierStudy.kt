package com.thread

import java.util.concurrent.CyclicBarrier
import kotlin.concurrent.thread

fun main() {
    // 注意，CyclicBarrier内部是使用ReentrantLock
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
        // 进程等待，等到有2个进程都在等待时，线程继续运行
        barrier.await()
    }
    thread {
        listOf(1, 2, 3, 4).forEach {
            while (!lock) {
            }
            print(it)
            lock = false
        }
        // 进程等待，等到有2个进程都在等待时，线程继续运行
        barrier.await()
    }
}