package com.thread

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
    val lock = ReentrantLock()
    val a = lock.newCondition()
    val b = lock.newCondition()
    val c = lock.newCondition()
    thread {
        // 获取锁
        lock.lock()
        listOf('a', 'b', 'c', 'd').forEach {
            // 等待条件a倍唤醒
            a.await()
            print(it)
            // 唤醒条件b
            b.signal()
        }
        // 唤醒条件c
        c.signal()
        // 释放锁
        lock.unlock()
    }
    thread {
        // 获取锁
        lock.lock()
        listOf(1, 2, 3, 4).forEach {
            print(it)
            // 唤醒条件a
            a.signal()
            // 等待条件b被唤醒
            b.await()
        }
        // 唤醒条件a
        a.signal()
        // 释放锁
        lock.unlock()
    }
    thread {
        // 获取锁
        lock.lock()
        // 等待条件c被唤醒
        c.await()
        listOf('A', 'B', 'C', 'D').forEach { print(it) }
        // 释放锁
        lock.unlock()
    }
}