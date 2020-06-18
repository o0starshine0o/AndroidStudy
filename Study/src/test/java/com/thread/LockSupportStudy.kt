package com.thread

import org.junit.Test
import java.util.concurrent.locks.LockSupport
import kotlin.concurrent.thread


class LockSupportStudy {

    @Test
    fun threadTest() {
        var a: Thread? = null
        val b = thread {
            listOf('a', 'b', 'c', 'd').forEach {
                LockSupport.park()
                print(it)
                LockSupport.unpark(a)
            }
        }
        a = thread {
            listOf(1, 2, 3, 4).forEach {
                print(it)
                LockSupport.unpark(b)
                LockSupport.park()
            }
        }
    }
}