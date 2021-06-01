package com.thread

import org.junit.Test
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class Foo {
    fun first() = println("first")
    fun second() = println("second")
    fun third() = println("third")

    @Test
    fun test0() {
        val threadA = thread(name = "threadA") { first() }
        val threadB = thread(name = "threadB") { threadA.join();second() }
        val threadC = thread(name = "threadC") { threadB.join();third() }
    }
}

class Foo1 {

    fun first() = println("first")
    fun second() = println("second")
    fun third() = println("third")

    @Test
    fun test0() {
        val lock = ReentrantLock()
        val lockB = lock.newCondition()
        val lockC = lock.newCondition()
        thread(name = "threadC") { lock.lock();lockC.await();third();lock.unlock() }
        thread(name = "threadB") { lock.lock();lockB.await();second();lockC.signalAll();lock.unlock() }
        thread(name = "threadA") { lock.lock();first();lockB.signalAll();lock.unlock()}


        Thread.sleep(1000)
    }
}