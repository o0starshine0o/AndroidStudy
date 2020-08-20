package com.protostar.download

import rx.Observable
import rx.functions.Func1
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


internal class RetryWhenNetworkException : Func1<Observable<out Throwable?>?, Observable<*>?> {
    // retry次数
    private var count = 3

    // 延迟
    private var delay = 3000L

    // 叠加延迟
    private var increaseDelay = 3000L

    constructor()

    constructor(count: Int, delay: Long) {
        this.count = count
        this.delay = delay
    }

    constructor(count: Int, delay: Long, increaseDelay: Long) {
        this.count = count
        this.delay = delay
        this.increaseDelay = increaseDelay
    }

    override fun call(o: Observable<out Throwable?>?) = o?.zipWith(Observable.range(1, count + 1)) { t, i -> Wrapper(t, i) }?.flatMap { w ->
        if (w.index ?: 0 < count + 1) {
            when (w?.throwable) {
                is ConnectException -> Observable.timer(delay + (w.index ?: 0 - 1) * increaseDelay, TimeUnit.MILLISECONDS)
                is SocketTimeoutException -> Observable.timer(delay + (w.index ?: 0 - 1) * increaseDelay, TimeUnit.MILLISECONDS)
                is TimeoutException -> Observable.timer(delay + (w.index ?: 0 - 1) * increaseDelay, TimeUnit.MILLISECONDS)
                else -> Observable.error<Any>(w?.throwable)
            }
        } else Observable.error<Any>(w?.throwable)
    }

    private inner class Wrapper(val throwable: Throwable?, val index: Int?)

}


