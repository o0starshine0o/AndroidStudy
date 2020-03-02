package com.designpattern.behavior.observer

import org.junit.Test

class Test {
    @Test
    fun test() {
        val subject = ConcreteSubject()
        subject.attach(Observer("0"))
        subject.attach(Observer("1"))
        subject.attach(Observer("2"))
    }
}