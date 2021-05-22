package com.thread

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun main(){
    val queue = LinkedBlockingQueue<Long>(10)
    val pool = Executors.newCachedThreadPool()
    val producer = pool.submit {
        val limit  = 10
        var time = 0
        while (time < limit){
            val sleep = Random.nextLong(1, 1000)
            Thread.sleep(sleep)
            queue.put(sleep)
            time++
        }
    }
    val consumer = pool.submit {
        while (!producer.isDone){
            // 如果使用take,consumer会无限等待
//            println("consumer get producer sleep time: ${queue.take()}")
            println("consumer get producer sleep time: ${queue.poll(1, TimeUnit.SECONDS)}")
        }
        println("producer.isDone")
    }
    while (!consumer.isDone){
        Thread.sleep(1000)
    }
    // 如果有pool未停止,程序是不会推出的
    pool.shutdown()
}