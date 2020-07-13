package com.abelhu.base

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

// 测试完成时间
@BenchmarkMode(Mode.AverageTime)
// 输出时间单位
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// 预热 2 轮，每次 1s
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
// 测试 5 轮，每次 3s
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
// fork 1 个进程程
@Fork(1)
// 每个测试线程一个实例
@State(Scope.Thread)
// 使用多少个线程测试
@Threads(Threads.MAX)
open class JMHTest {
    companion object {
        const val NUMBER = 9
    }

    @Benchmark
    fun switchTest() {
        val num1 = when (NUMBER) {
            1 -> 1
            3 -> 3
            5 -> 5
            7 -> 7
            9 -> 9
            else -> -1
        }
    }

    @Benchmark
    fun ifTest() {
        var num1 = 0
        if (NUMBER == 1) {
            num1 = 1
        } else if (NUMBER == 3) {
            num1 = 3
        } else if (NUMBER == 5) {
            num1 = 5
        } else if (NUMBER == 7) {
            num1 = 7
        } else if (NUMBER == 9) {
            num1 = 9
        } else {
            num1 = -1
        }
    }
}

fun main() {
    Runner(OptionsBuilder().include(JMHTest::class.java.simpleName).build()).run()
}