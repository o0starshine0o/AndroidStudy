package com.thread

import org.junit.Test
import kotlin.concurrent.thread

class ThreadLocalStudy {
    companion object {
        @JvmStatic
        private val local = ThreadLocal<Int>()
    }

    @Test
    fun test() {
        val thread1 = thread(name = "thread-1") {
            IntRange(0, 9).forEach { local.set(it).apply { Thread.sleep(10) }.apply { println("${Thread.currentThread().name} set local: ${local.get()}") } }
            local.remove()
        }
        val thread2 = thread(name = "thread-2") {
            IntRange(0, 9).forEach { local.set(it).apply { Thread.sleep(10) }.apply { println("${Thread.currentThread().name} set local: ${local.get()}") } }
            local.remove()
        }
        val thread3 = thread(name = "thread-3") {
            IntRange(0, 9).forEach { local.set(it).apply { Thread.sleep(10) }.apply { println("${Thread.currentThread().name} set local: ${local.get()}") } }
            local.remove()
        }
        thread1.join()
        thread2.join()
        thread3.join()
    }
}