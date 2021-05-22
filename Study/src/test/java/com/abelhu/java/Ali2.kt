package com.abelhu.java

import java.util.concurrent.FutureTask
import kotlin.concurrent.thread
import kotlin.random.Random


private val D: Int = 20
private val N: Int = 2
private val map = HashMap<Thread, Int>()


fun main() {
    for (i in 0..N) {
        thread(name = i.toString(), isDaemon = true) {
            val speed = Random.nextInt(1, 10)
            var distence = 0

            while (distence < D) {
                distence += speed
                map[Thread.currentThread()] = distence

                Thread.sleep(1000)
            }
        }
    }
    val future = FutureTask {
        while (true) {
            println("-------------------------")
            map.forEach { (thread, distance) ->
                println("Thread[${thread.name}]:run $distance")
            }
            Thread.sleep(1000)
        }
    }
    future.run()
    while (true) {
    }
}

