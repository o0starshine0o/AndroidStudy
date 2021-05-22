package com.thread

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


fun main() {
    // LinkedBlockingQueue
    val fixedPool = Executors.newFixedThreadPool(2)
    // SynchronousQueue
    val cachedPool = Executors.newCachedThreadPool()
    // LinkedBlockingQueue
    val singlePool = Executors.newSingleThreadExecutor()
    // DelayedWorkQueue
    val schedulePool = Executors.newScheduledThreadPool(1)

    cachedPool.isTerminated
    cachedPool.shutdown()
    cachedPool.shutdownNow()
    cachedPool.awaitTermination(1 ,TimeUnit.SECONDS)
}