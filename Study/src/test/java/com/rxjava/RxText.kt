package com.rxjava

import io.reactivex.rxjava3.core.Observable

class RxText {
    companion object {
        val TRIGGER = Any()

        @JvmStatic
        fun main(params: Array<String>) {
            rxTest()
        }

        private fun rxTest() {
//            val list = List(4) { PublishSubject.create<Int>() }
//            Observable.concat(Observable.fromIterable(list)).buffer(4).subscribe { }
//            Observable.concat(Observable.fromIterable(list)).buffer(4).subscribe { println(it) }
//            list.forEachIndexed { index, subject -> subject.onNext(index);subject.onComplete() }

            Observable.merge(Observable.just(TRIGGER), Observable.just(TRIGGER)).flatMap { Observable.just(it).apply { println(it) } }.subscribe()
            Observable.merge(Observable.just(TRIGGER), Observable.just(TRIGGER)).flatMap { Observable.just(it).apply { println(it) } }.subscribe()
        }
    }
}