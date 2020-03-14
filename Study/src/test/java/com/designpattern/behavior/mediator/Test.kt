package com.designpattern.behavior.mediator

import org.junit.Test

class Test {
    @Test
    fun test() {
        // 先创建中介者
        val mediator = ConcreteMediator()
        // 创建2个参与者， 并且将中介者和参与者相互关联
        val a = ColleagueA(mediator)
        val b = ColleagueB(mediator)
        // A 发消息给 B, a并没有使用b的实例，达到解耦的目的，如果B不存在，消息发送失败
        a.send("I'm A", "B")
        // B 发消息给 A, b并没有使用a的实例，达到解耦的目的，如果A不存在，消息发送失败
        b.send("I'm B", "A")
    }
}