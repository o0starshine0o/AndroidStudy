package com.thread

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
    // 注意，CyclicBarrier内部是使用ReentrantLock
    val barrier = CyclicBarrier(2) {
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
    }
    val lock = ReentrantLock()
    val char = lock.newCondition()
    val num = lock.newCondition()
    var start = 0
    thread {
        listOf('a', 'b', 'c', 'd').forEach {
            try {
                lock.lock()
                while (start <= 0) char.await()
                print(it)
                num.signalAll()
                if ('d' != it) char.await()
            } finally {
                lock.unlock()
            }
        }
        // 进程等待，等到有2个进程都在等待时，线程继续运行
        barrier.await()
    }
    thread {
        listOf(1, 2, 3, 4).forEach {
            try {
                lock.lock()
                start = it
                print(it)
                char.signalAll()
                if (4 != it) num.await()
            } finally {
                lock.unlock()
            }
        }
        // 进程等待，等到有2个进程都在等待时，线程继续运行
        barrier.await()
    }
}