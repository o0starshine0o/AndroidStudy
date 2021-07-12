package com.coroutines

import kotlinx.coroutines.*
import org.junit.Test

class CoroutinesStudy {

    // 这是你的第一个挂起函数
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }

    @Test
    fun test0() = runBlocking {
        // 在后台启动一个新的协程并继续,外部协程需要等待内部协程执行完成才结束
        launch {
            // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            delay(1000L)
            // 在延迟后打印输出
            println("World!")
        }
        // 在协程内部可以像普通函数一样使用挂起函数
        launch { doWorld() }
        // 创建一个协程作用域,会挂起(而非阻塞)当前协程
        coroutineScope {
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }
        // 协程已在等待时主线程还在继续
        println("Hello,")
    }
}