package com.designpattern.behavior.observer

import java.util.*

abstract class ISubject {
    // 使用Vector保证线程安全
    private val listeners = Vector<IObserver>()

    fun attach(listener: IObserver) {
        listeners.add(listener)
    }

    fun detach(listener: IObserver) {
        listeners.remove(listener)
    }

    fun update() {
        for (listener in listeners) listener.update()
    }
}