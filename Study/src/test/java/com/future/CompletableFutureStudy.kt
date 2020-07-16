package com.future

import org.junit.Test
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

class CompletableFutureStudy {

    @Test
    fun testTask() {
        val task = CompletableFuture<String>()
        thread { task.complete("CompletableFuture complete") }
        println(task.get())
    }

    @Test
    fun testStatic() {
        val task = CompletableFuture.supplyAsync { "CompletableFuture supplyAsync" }
        println(task.get())
    }

    @Test
    fun testAll() {
        val task0 = CompletableFuture.supplyAsync { "CompletableFuture task0".apply { println(this) } }
        val task1 = CompletableFuture.supplyAsync { "CompletableFuture task1".apply { println(this) } }
        CompletableFuture.anyOf(task0, task1).get().apply { println(this) }
        CompletableFuture.allOf(task0, task1).get().apply { println("all of") }
    }

    @Test
    fun testWhen() {
        val task = CompletableFuture.supplyAsync { "CompletableFuture" }.whenComplete { t, u -> "first task".apply { println(this) } }
        println(task.get())
    }
}